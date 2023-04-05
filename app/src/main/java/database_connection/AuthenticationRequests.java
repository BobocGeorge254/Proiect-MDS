package database_connection;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import others.Manager;

public class AuthenticationRequests {

    public static String registerUser(String email, String username, String password) {
        CollectionReference usersCollection = Manager.dbConnection.getDatabase().collection("Users");
        Query queryCheckIfEmailExists = usersCollection.whereEqualTo("email", email);

        Task<QuerySnapshot> queryEmailSnapshot = queryCheckIfEmailExists.get();

        while (!queryEmailSnapshot.isComplete()) {
        }  //blocks until query is executed

        if (!queryEmailSnapshot.getResult().isEmpty())
            return "Email is already used";

        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("username", username);
        user.put("password", password);

        Task<DocumentReference> addUserTask = usersCollection.add(user);
        while (!addUserTask.isComplete()) {
        }

        if (addUserTask.isSuccessful())
            return "User added successfully";

        return "Error adding user";
    }

    public static String checkUserExists(String email, String username, String password) {  //also used for log in
        CollectionReference usersCollection = Manager.dbConnection.getDatabase().collection("Users");

        Query queryByEmailAndPassword = usersCollection.whereEqualTo("email", email).whereEqualTo("password", password);
        Task<QuerySnapshot> queryEmailTask = queryByEmailAndPassword.get();

        while (!queryEmailTask.isComplete()) {
        }   //blocks until query is executed

        if (!queryEmailTask.getResult().isEmpty())
            return queryEmailTask.getResult().getDocuments().get(0).getId();  //if login success return the id of the client

        Query queryByUsernameAndPassword = usersCollection.whereEqualTo("username", username).whereEqualTo("password", password);
        Task<QuerySnapshot> queryUsernameTask = queryByUsernameAndPassword.get();

        while (!queryUsernameTask.isComplete()) {
        }

        if (!queryUsernameTask.getResult().isEmpty())
            return queryUsernameTask.getResult().getDocuments().get(0).getId();  //if login success return the id of the client

        return "User does not exist yet";
    }
}
