package database_connection;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import others.Manager;

public class OtherRequests {

    public static String getUsernameByUserId(String userId) {
        CollectionReference usersCollection = Manager.dbConnection.getDatabase().collection("Users");
        Task<DocumentSnapshot> getUserTask = usersCollection.document(userId).get();

        while (!getUserTask.isComplete()) {}   //blocks until query is executed

        String username = getUserTask.getResult().get("username").toString();

        return username;
    }
    public static void updateUser(String id, String username, String email, String password) {
        CollectionReference usersCollection = Manager.dbConnection.getDatabase().collection("Users");

        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);
        user.put("password", password);

        DocumentReference userDocRef = usersCollection.document(id);
        userDocRef.update(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // User updated successfully
                        // You can perform additional actions here if needed
                    }
                }) ;
    }



}
