package database_connection;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import others.Manager;

public class OtherRequests {

    public static String getUsernameByUserId(String userId) {
        CollectionReference usersCollection = Manager.dbConnection.getDatabase().collection("Users");
        Task<DocumentSnapshot> getUserTask = usersCollection.document(userId).get();

        while (!getUserTask.isComplete()) {}   //blocks until query is executed

        String username = getUserTask.getResult().get("username").toString();

        return username;
    }
}
