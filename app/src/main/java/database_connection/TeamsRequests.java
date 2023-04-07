package database_connection;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import others.Manager;
import teams.DataTeamCard;

public class TeamsRequests {

    public static String createTeam(String teamName, String teamDescription, String userId) {
        CollectionReference teamsCollection = Manager.dbConnection.getDatabase().collection("Teams");

        Map<String, Object> team = new HashMap<>();
        team.put("name", teamName);
        team.put("description", teamDescription);

        Task<DocumentReference> addTeamTask = teamsCollection.add(team);
        while (!addTeamTask.isComplete()) {}

        if (addTeamTask.isSuccessful()) {
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
}
