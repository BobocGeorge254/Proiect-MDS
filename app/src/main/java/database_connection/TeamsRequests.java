package database_connection;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import others.Manager;
import teams.DataTeamCard;
import teams.DataTeamChanelNameCard;
import teams.DataTeamFile;
import teams.DataTeamPost;
import teams.DataTeamPostReply;

public class TeamsRequests {

    public static String createTeam(String teamName, String teamDescription, String photoUri, String userId) {
        CollectionReference teamsCollection = Manager.dbConnection.getDatabase().collection("Teams");

        Map<String, Object> team = new HashMap<>();
        team.put("name", teamName);
        team.put("description", teamDescription);
        team.put("photo_uri", photoUri);

        Task<DocumentReference> addTeamTask = teamsCollection.add(team);
        while (!addTeamTask.isComplete()) {}

        if (addTeamTask.isSuccessful()) {
            //adding team default General chanel
            CollectionReference teams_chanelsCollection = Manager.dbConnection.getDatabase().collection("Teams_Chanels");
            Map<String, Object> chanel = new HashMap<>();
            chanel.put("team_id", addTeamTask.getResult().getId());
            chanel.put("name", "General");
            chanel.put("timestamp", FieldValue.serverTimestamp());

            Task<DocumentReference> addChanelToTeamTask = teams_chanelsCollection.add(chanel);
            while (!addChanelToTeamTask.isComplete()) {}
            if (!addChanelToTeamTask.isSuccessful())
                return "Error adding team";

            //adding user to team
            CollectionReference users_teamsCollection = Manager.dbConnection.getDatabase().collection("Users_Teams");
            Map<String, Object> user = new HashMap<>();
            user.put("user_id", userId);
            user.put("team_id", addTeamTask.getResult().getId());
            user.put("role", "Admin");

            Task<DocumentReference> addUsers_TeamsTask = users_teamsCollection.add(user);
            while (!addUsers_TeamsTask.isComplete()) {}

            if (addUsers_TeamsTask.isSuccessful())
                return addTeamTask.getResult().getId();
        }
        return "Error adding team";
    }

    public static String addUserToTeam(String userId, String teamId, String role) {
        CollectionReference users_teamsCollection = Manager.dbConnection.getDatabase().collection("Users_Teams");
        CollectionReference teamsCollection = Manager.dbConnection.getDatabase().collection("Teams");
        Query queryCheckJointTeam = users_teamsCollection.whereEqualTo("user_id", userId).whereEqualTo("team_id", teamId);

        Task<DocumentSnapshot> checkTeamExistTask = teamsCollection.document(teamId).get();
        while (!checkTeamExistTask.isComplete()) {}  //blocks until query is executed
        if(!checkTeamExistTask.getResult().exists())
            return "Team doesn't exist";

        Task<QuerySnapshot> queryCheckJoinTask = queryCheckJointTeam.get();

        while (!queryCheckJoinTask.isComplete()) {
        }   //blocks until query is executed

        if(!queryCheckJoinTask.getResult().isEmpty())
            return "User already in team";

        Map<String, Object> user = new HashMap<>();
        user.put("user_id", userId);
        user.put("team_id", teamId);
        user.put("role", role);

        Task<DocumentReference> addUsers_TeamsTask = users_teamsCollection.add(user);
        while (!addUsers_TeamsTask.isComplete()) {}

        if (addUsers_TeamsTask.isSuccessful())
            return teamId;

        return "Error adding user to team";
    }

    public static ArrayList<DataTeamCard> getTeams(String userId) {
        CollectionReference users_teamsCollection = Manager.dbConnection.getDatabase().collection("Users_Teams");
        CollectionReference teamsCollection = Manager.dbConnection.getDatabase().collection("Teams");

        Query queryGetTeams = users_teamsCollection.whereEqualTo("user_id", userId);
        Task<QuerySnapshot> queryGetTeamsTask = queryGetTeams.get();

        while (!queryGetTeamsTask.isComplete()) {
        }   //blocks until query is executed

        ArrayList<DataTeamCard> teamsList = new ArrayList<>();

        if (!queryGetTeamsTask.getResult().isEmpty())
            for(DocumentSnapshot documentSnapshot : queryGetTeamsTask.getResult().getDocuments()) {
                String id = documentSnapshot.getString("team_id");
                Task<DocumentSnapshot> getTeamDetailsTask = teamsCollection.document(id).get();

                while (!getTeamDetailsTask.isComplete()) {}  //blocks until query is executed

                String name = getTeamDetailsTask.getResult().get("name").toString();
                String description = getTeamDetailsTask.getResult().get("description").toString();
                String photoUrl = getTeamDetailsTask.getResult().get("photo_uri").toString();
                teamsList.add(new DataTeamCard(id, name, description, photoUrl));
            }

        return teamsList;
    }

    public static ArrayList<DataTeamChanelNameCard> getTeamsChanels(String teamId) {
        CollectionReference teams_chanelsCollection = Manager.dbConnection.getDatabase().collection("Teams_Chanels");

        Query queryGetChanels = teams_chanelsCollection.whereEqualTo("team_id", teamId);
        Task<QuerySnapshot> queryGetChanelsTask = queryGetChanels.get();

        while (!queryGetChanelsTask.isComplete()) {
        }   //blocks until query is executed

        ArrayList<DataTeamChanelNameCard> chanelsList = new ArrayList<>();

        if (!queryGetChanelsTask.getResult().isEmpty()) {
            Map<Timestamp, DataTeamChanelNameCard> chanelsMap = new HashMap<>();

            for(DocumentSnapshot documentSnapshot : queryGetChanelsTask.getResult().getDocuments()) {
                String id = documentSnapshot.getId();
                String name = documentSnapshot.getString("name");
                Timestamp timestamp = documentSnapshot.getTimestamp("timestamp");

                chanelsMap.put(timestamp, new DataTeamChanelNameCard(id, name, teamId));
            }

            ArrayList<Timestamp> timestampList = new ArrayList<>(chanelsMap.keySet());   //ordering teams by creating time
            timestampList.sort(Comparator.comparing(Timestamp::getSeconds));
            for(Timestamp timestamp : timestampList)
                for(Timestamp key : chanelsMap.keySet())
                    if(timestamp.equals(key))
                        chanelsList.add(chanelsMap.get(key));
        }

        return chanelsList;
    }

    public static String addChanel(String teamId, String name) {
        CollectionReference teams_chanelsCollection = Manager.dbConnection.getDatabase().collection("Teams_Chanels");

        Map<String, Object> chanel = new HashMap<>();
        chanel.put("team_id", teamId);
        chanel.put("name", name);
        chanel.put("timestamp", FieldValue.serverTimestamp());

        Task<DocumentReference> addChanelTask = teams_chanelsCollection.add(chanel);
        while (!addChanelTask.isComplete()) {}

        if (addChanelTask.isSuccessful())
            return addChanelTask.getResult().getId();

        return "Error adding chanel";
    }

    public static ArrayList<DataTeamPost> getTeamsPosts(String teamChanelId) {
        CollectionReference teams_postsCollection = Manager.dbConnection.getDatabase().collection("Teams_Posts");

        Query queryGetPosts = teams_postsCollection.whereEqualTo("team_chanel_id", teamChanelId);
        Task<QuerySnapshot> queryGetPostsTask = queryGetPosts.get();

        while (!queryGetPostsTask.isComplete()) {
        }   //blocks until query is executed

        ArrayList<DataTeamPost> postsList = new ArrayList<>();

        if (!queryGetPostsTask.getResult().isEmpty()) {
            Map<Timestamp, DataTeamPost> postsMap = new HashMap<>();

            for(DocumentSnapshot documentSnapshot : queryGetPostsTask.getResult().getDocuments()) {
                String id = documentSnapshot.getId();
                String senderId = documentSnapshot.getString("sender_id");
                String teamId = documentSnapshot.getString("team_id");
                String text = documentSnapshot.getString("text");
                String sendDate = documentSnapshot.getString("date_posted");
                String senderName = OtherRequests.getUsernameByUserId(senderId);
                Timestamp timestamp = documentSnapshot.getTimestamp("timestamp");

                postsMap.put(timestamp, new DataTeamPost(id, senderName, text, sendDate, senderId, teamId));
            }

            ArrayList<Timestamp> timestampList = new ArrayList<>(postsMap.keySet());   //ordering posts by post time
            timestampList.sort(Comparator.comparing(Timestamp::getSeconds));
            for(Timestamp timestamp : timestampList)
                for(Timestamp key : postsMap.keySet())
                    if(timestamp.equals(key))
                        postsList.add(postsMap.get(key));
        }

        return postsList;
    }

    public static String addTeamPost(String senderId, String teamChanelId, String teamId, String text) {
        CollectionReference teams_postsCollection = Manager.dbConnection.getDatabase().collection("Teams_Posts");

        Map<String, Object> post = new HashMap<>();
        post.put("date_posted", LocalDate.now().toString());
        post.put("sender_id", senderId);
        post.put("team_chanel_id", teamChanelId);
        post.put("team_id", teamId);
        post.put("text", text);
        post.put("timestamp", FieldValue.serverTimestamp());

        Task<DocumentReference> addPostTask = teams_postsCollection.add(post);
        while (!addPostTask.isComplete()) {}

        if (addPostTask.isSuccessful())
            return addPostTask.getResult().getId();

        return "Error adding team post";
    }

    public static String addTeamPostReply(String senderId, String teamPostId, String teamChanelId, String teamId, String text) {
        CollectionReference teams_postsRepliesCollection = Manager.dbConnection.getDatabase().collection("Teams_Posts_Replies");

        Map<String, Object> reply = new HashMap<>();
        reply .put("date_posted", LocalDate.now().toString());
        reply .put("sender_id", senderId);
        reply .put("team_post_id", teamPostId);
        reply .put("team_chanel_id", teamChanelId);
        reply .put("team_id", teamId);
        reply .put("text", text);
        reply .put("timestamp", FieldValue.serverTimestamp());

        Task<DocumentReference> addPostReplyTask = teams_postsRepliesCollection.add(reply);
        while (!addPostReplyTask.isComplete()) {}

        if (addPostReplyTask.isSuccessful())
            return addPostReplyTask.getResult().getId();

        return "Error adding team post reply";
    }

    public static ArrayList<DataTeamPostReply> getTeamsPostsReplies(String teamPostId) {
        CollectionReference teams_postsRepliesCollection = Manager.dbConnection.getDatabase().collection("Teams_Posts_Replies");

        Query queryGetReplies = teams_postsRepliesCollection.whereEqualTo("team_post_id", teamPostId);
        Task<QuerySnapshot> queryGetRepliesTask = queryGetReplies.get();

        while (!queryGetRepliesTask.isComplete()) {
        }   //blocks until query is executed

        ArrayList<DataTeamPostReply> repliesList = new ArrayList<>();

        if (!queryGetRepliesTask.getResult().isEmpty()) {
            Map<Timestamp, DataTeamPostReply> postsMap = new HashMap<>();

            for(DocumentSnapshot documentSnapshot : queryGetRepliesTask.getResult().getDocuments()) {
                String id = documentSnapshot.getId();
                String senderId = documentSnapshot.getString("sender_id");
                String teamId = documentSnapshot.getString("team_id");
                String text = documentSnapshot.getString("text");
                String sendDate = documentSnapshot.getString("date_posted");
                String senderName = OtherRequests.getUsernameByUserId(senderId);
                Timestamp timestamp = documentSnapshot.getTimestamp("timestamp");

                postsMap.put(timestamp, new DataTeamPostReply(id, senderName, text, sendDate, senderId, teamPostId, teamId));
            }

            ArrayList<Timestamp> timestampList = new ArrayList<>(postsMap.keySet());   //ordering posts by post time
            timestampList.sort(Comparator.comparing(Timestamp::getSeconds));
            for(Timestamp timestamp : timestampList)
                for(Timestamp key : postsMap.keySet())
                    if(timestamp.equals(key))
                        repliesList.add(postsMap.get(key));
        }

        return repliesList;
    }

    public static String deleteTeam(String teamId) {
        CollectionReference teams_Collection = Manager.dbConnection.getDatabase().collection("Teams");
        CollectionReference teams_postsCollection = Manager.dbConnection.getDatabase().collection("Teams_Posts");
        CollectionReference users_teamsCollection = Manager.dbConnection.getDatabase().collection("Users_Teams");
        CollectionReference team_chanelRepliesCollection = Manager.dbConnection.getDatabase().collection("Teams_Chanels");

        String logoUri = getTeamData(teamId).getPhotoUri();   //used to

        Query queryGetPosts = teams_postsCollection.whereEqualTo("team_id", teamId);
        Task<QuerySnapshot> queryGetPostsTask = queryGetPosts.get();

        while (!queryGetPostsTask.isComplete()) {}   //blocks until query is executed

        if(!queryGetPostsTask.getResult().isEmpty())
            for(DocumentSnapshot documentSnapshot : queryGetPostsTask.getResult().getDocuments())  //deletes the posts of this team
                deleteTeamPost(documentSnapshot.getId());

        Task<Void> deleteTeamTask = teams_Collection.document(teamId).delete();
        while (!deleteTeamTask.isComplete()) {}

        if (deleteTeamTask.isSuccessful()) {   //now we delete the users_teams references and team chanels
            FileRequest.deleteFile(logoUri);

            Query queryGetUsersTeams = users_teamsCollection.whereEqualTo("team_id", teamId);
            Task<QuerySnapshot> queryGetUsersTeamsTask = queryGetUsersTeams.get();

            while (!queryGetUsersTeamsTask.isComplete()) {}   //blocks until query is executed

            if(!queryGetUsersTeamsTask.getResult().isEmpty())
                for(DocumentSnapshot documentSnapshot : queryGetUsersTeamsTask.getResult().getDocuments()) {
                    String userToDeleteFromTeamId = documentSnapshot.getString("user_id");
                    deleteUserFromTeam(userToDeleteFromTeamId, teamId);
                }

            Query queryGetTeamChanels = team_chanelRepliesCollection.whereEqualTo("team_id", teamId);  //now delete team chanels
            Task<QuerySnapshot> getTeamsChanelsTask = queryGetTeamChanels.get();

            while (!getTeamsChanelsTask.isComplete()) {}

            if(!getTeamsChanelsTask.getResult().isEmpty()) {
                for(DocumentSnapshot documentSnapshot : getTeamsChanelsTask.getResult())
                    deleteTeamChanel(documentSnapshot.getId());
            }
        } else
            return "Failed to delete team";

        return "Team deleted successfully";
    }

    public static String deleteTeamPost(String teamPostId) {
        CollectionReference teams_postsCollection = Manager.dbConnection.getDatabase().collection("Teams_Posts");
        CollectionReference teams_postsRepliesCollection = Manager.dbConnection.getDatabase().collection("Teams_Posts_Replies");

        Query queryGetReplies = teams_postsRepliesCollection.whereEqualTo("team_post_id", teamPostId);
        Task<QuerySnapshot> queryGetRepliesTask = queryGetReplies.get();

        while (!queryGetRepliesTask.isComplete()) {}   //blocks until query is executed

        if(!queryGetRepliesTask.getResult().isEmpty())
            for(DocumentSnapshot documentSnapshot : queryGetRepliesTask.getResult().getDocuments())  //deletes the replies on this post
                deleteTeamPostReply(documentSnapshot.getId());

        Task<Void> deletePostTask = teams_postsCollection.document(teamPostId).delete();
        while (!deletePostTask.isComplete()) {}

        if (deletePostTask.isSuccessful())
            return "Post deleted successfully";

        return "Error deleting post";
    }

    public static String deleteTeamPostReply(String teamPostReplyId) {
        CollectionReference teams_postsRepliesCollection = Manager.dbConnection.getDatabase().collection("Teams_Posts_Replies");

        Task<Void> deletePostReplyTask = teams_postsRepliesCollection.document(teamPostReplyId).delete();
        while (!deletePostReplyTask.isComplete()) {}

        if (deletePostReplyTask.isSuccessful())
            return "Post reply deleted successfully";

        return "Error deleting team post reply";
    }

    public static String deleteUserFromTeam(String userId, String teamId) {
        CollectionReference users_teamsRepliesCollection = Manager.dbConnection.getDatabase().collection("Users_Teams");

        Query queryGetUsersTeams = users_teamsRepliesCollection.whereEqualTo("user_id", userId).whereEqualTo("team_id", teamId);
        Task<QuerySnapshot> getUsersTeamsTask = queryGetUsersTeams.get();

        while (!getUsersTeamsTask.isComplete()) {}

        if(!getUsersTeamsTask.getResult().isEmpty()) {
            Task<Void> deleteUserFromTeamTask = users_teamsRepliesCollection.document(getUsersTeamsTask.getResult().getDocuments().get(0).getId()).delete();
            while (!deleteUserFromTeamTask.isComplete()) {}

            if (deleteUserFromTeamTask.isSuccessful())
                return "User removed from team successfully";
        }

        return "Error removing user from team";
    }

    public static String changeRoleInTeam(String userId, String teamId, String newRole) {
        CollectionReference users_teamsRepliesCollection = Manager.dbConnection.getDatabase().collection("Users_Teams");

        Query queryGetUsersTeams = users_teamsRepliesCollection.whereEqualTo("user_id", userId).whereEqualTo("team_id", teamId);
        Task<QuerySnapshot> getUsersTeamsTask = queryGetUsersTeams.get();

        while (!getUsersTeamsTask.isComplete()) {}

        if(!getUsersTeamsTask.getResult().isEmpty()) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("role", newRole);

            Task<Void> changeRoleTask = users_teamsRepliesCollection.document(getUsersTeamsTask.getResult().getDocuments().get(0).getId()).update(updates);
            while (!changeRoleTask.isComplete()) {}

            if(changeRoleTask.isSuccessful())
                return "Changed team member role successfully";
        }

        return "Error changing team member role";
    }

    public static String deleteTeamChanel(String teamChanelId) {
        CollectionReference teams_chanelRepliesCollection = Manager.dbConnection.getDatabase().collection("Teams_Chanels");

        Task<Void> deleteChanelTask = teams_chanelRepliesCollection.document(teamChanelId).delete();
        while (!deleteChanelTask.isComplete()) {}

        if (deleteChanelTask.isSuccessful())
            return "Chanel deleted successfully";

        return "Error deleting chanel";
    }

    public static String getTeamRole(String userId, String teamId) {
        CollectionReference users_teamsRepliesCollection = Manager.dbConnection.getDatabase().collection("Users_Teams");

        Query queryGetUsersTeams = users_teamsRepliesCollection.whereEqualTo("user_id", userId).whereEqualTo("team_id", teamId);
        Task<QuerySnapshot> queryGetUsersTeamsTask = queryGetUsersTeams.get();

        while (!queryGetUsersTeamsTask.isComplete()) {}   //blocks until query is executed

        if(!queryGetUsersTeamsTask.getResult().isEmpty())
            return  queryGetUsersTeamsTask.getResult().getDocuments().get(0).get("role").toString();

        return "Error checking team member role";
    }

    public static DataTeamCard getTeamData(String teamId) {
        CollectionReference teamsCollection = Manager.dbConnection.getDatabase().collection("Teams");

        Task<DocumentSnapshot> getTeamDetailsTask = teamsCollection.document(teamId).get();

        while (!getTeamDetailsTask.isComplete()) {}  //blocks until query is executed

        String name = getTeamDetailsTask.getResult().get("name").toString();
        String description = getTeamDetailsTask.getResult().get("description").toString();
        String photoUrl = getTeamDetailsTask.getResult().get("photo_uri").toString();

        return new DataTeamCard(teamId, name, description, photoUrl);
    }

    public static DataTeamChanelNameCard getTeamChanelData(String teamChanelId) {
        CollectionReference teams_chanelsCollection = Manager.dbConnection.getDatabase().collection("Teams_Chanels");

        Task<DocumentSnapshot> getChanelDetailsTask = teams_chanelsCollection.document(teamChanelId).get();

        while (!getChanelDetailsTask.isComplete()) {}  //blocks until query is executed

        String name = getChanelDetailsTask.getResult().get("name").toString();
        String teamId = getChanelDetailsTask.getResult().get("team_id").toString();

        return new DataTeamChanelNameCard(teamChanelId, name, teamId);
    }

    public static String updateTeam(String teamId, String updatedTeamName, String updatedTeamDescription, String updatedPhotoUri) {
        CollectionReference teamsCollection = Manager.dbConnection.getDatabase().collection("Teams");

        Map<String, Object> updates = new HashMap<>();
        updates.put("name", updatedTeamName);
        updates.put("description", updatedTeamDescription);
        updates.put("photo_uri", updatedPhotoUri);

        Task<Void> updateTeamTask = teamsCollection.document(teamId).update(updates);
        while (!updateTeamTask.isComplete()) {}

        if(updateTeamTask.isSuccessful())
            return "Updated team successfully";

        return "Error updating team";
    }

    public static String addTeamFile(String fileName, String uri, String team_id) {
        CollectionReference teams_filesCollection = Manager.dbConnection.getDatabase().collection("Teams_Files");

        Map<String, Object> post = new HashMap<>();
        post.put("name", fileName);
        post.put("uri", uri);
        post.put("team_id", team_id);

        Task<DocumentReference> addFileTask = teams_filesCollection.add(post);
        while (!addFileTask.isComplete()) {}

        if (addFileTask.isSuccessful())
            return addFileTask.getResult().getId();

        return "Error adding team file";
    }

    public static ArrayList<DataTeamFile> getTeamsFilesData(String teamId) {
        CollectionReference teams_filesCollection = Manager.dbConnection.getDatabase().collection("Teams_Files");

        Query queryGetFiles = teams_filesCollection.whereEqualTo("team_id", teamId);
        Task<QuerySnapshot> queryGetFilesTask = queryGetFiles.get();

        while (!queryGetFilesTask.isComplete()) {
        }   //blocks until query is executed

        ArrayList<DataTeamFile> filesList = new ArrayList<>();

        if (!queryGetFilesTask.getResult().isEmpty())
            for(DocumentSnapshot documentSnapshot : queryGetFilesTask.getResult().getDocuments()) {
                String id = documentSnapshot.getId();
                String name = documentSnapshot.getString("name");
                String uri = documentSnapshot.getString("uri");

                filesList.add(new DataTeamFile(id, uri, name, teamId));
        }

        return filesList;
    }

    public static String deleteTeamFile(String teamFileId) {
        CollectionReference teams_filesRepliesCollection = Manager.dbConnection.getDatabase().collection("Teams_Files");

        Task<Void> deleteFileTask = teams_filesRepliesCollection.document(teamFileId).delete();
        while (!deleteFileTask.isComplete()) {}

        if (deleteFileTask.isSuccessful())
            return "File deleted successfully";

        return "Error deleting file";
    }
}
