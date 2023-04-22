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
import teams.DataTeamPost;
import teams.DataTeamPostReply;

public class TeamsRequests {

    public static String createTeam(String teamName, String teamDescription, String userId) {
        CollectionReference teamsCollection = Manager.dbConnection.getDatabase().collection("Teams");

        Map<String, Object> team = new HashMap<>();
        team.put("name", teamName);
        team.put("description", teamDescription);

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
                teamsList.add(new DataTeamCard(id, name, description));
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

                chanelsMap.put(timestamp, new DataTeamChanelNameCard(id, name));
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
                String text = documentSnapshot.getString("text");
                String sendDate = documentSnapshot.getString("date_posted");
                String senderName = OtherRequests.getUsernameByUserId(senderId);
                Timestamp timestamp = documentSnapshot.getTimestamp("timestamp");

                postsMap.put(timestamp, new DataTeamPost(id, senderName, text, sendDate, senderId));
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
                String text = documentSnapshot.getString("text");
                String sendDate = documentSnapshot.getString("date_posted");
                String senderName = OtherRequests.getUsernameByUserId(senderId);
                Timestamp timestamp = documentSnapshot.getTimestamp("timestamp");

                postsMap.put(timestamp, new DataTeamPostReply(id, senderName, text, sendDate, senderId, teamPostId));
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

    public static String deleteTeamPostReply(String teamPostReplyId) {
        CollectionReference teams_postsRepliesCollection = Manager.dbConnection.getDatabase().collection("Teams_Posts_Replies");

        Task<Void> deletePostReplyTask = teams_postsRepliesCollection.document(teamPostReplyId).delete();
        while (!deletePostReplyTask.isComplete()) {}

        if (deletePostReplyTask.isSuccessful())
            return "Post reply deleted successfully";

        return "Error deleting team post reply";
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
}
