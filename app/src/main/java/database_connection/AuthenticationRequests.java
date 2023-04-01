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

    public String registerUser(String email, String username, String password) {

        if(checkUserExists(email, username, password).equals("User already exists"))
            return "User already exists";
        else if(checkUserExists(email, username, password).equals("Couldn't check if user is already in database"))
            return "Couldn't check if user is already in database";

        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("username", username);
        user.put("password", password);

        CollectionReference usersCollection = Manager.dbConnection.getDatabase().collection("Users");

        if(usersCollection.add(user).isSuccessful())
            return "User added successfully";

        return  "Error adding user";
    }

    public String checkUserExists(String email, String username, String password) {
        CollectionReference usersCollection = Manager.dbConnection.getDatabase().collection("Users");

        Query queryByUsernameAndPassword = usersCollection.whereEqualTo("username", username).whereEqualTo("password", password);
        Query queryByEmailAndPassword = usersCollection.whereEqualTo("email", email).whereEqualTo("password", password);

        final boolean[] userExists = {false};
        final CountDownLatch latch = new CountDownLatch(2);

        queryByEmailAndPassword.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot queryEmailSnapshot = task.getResult();
                    if (!queryEmailSnapshot.isEmpty()) {
                        userExists[0] = true;
                    }
                }
                latch.countDown();
            }
        });

        queryByUsernameAndPassword.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot queryUsernameSnapshot = task.getResult();
                    if (!queryUsernameSnapshot.isEmpty()) {
                        userExists[0] = true;
                    }
                }
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Error checking if user exists";
        }

        if (userExists[0]) {
            return "User already exists";
        } else {
            return "User does not exist";
        }
    }

}
