package database_connection;


import com.google.firebase.firestore.FirebaseFirestore;

public class DbConnection {
    private final FirebaseFirestore database;

    public DbConnection() {
        database = FirebaseFirestore.getInstance();
    }

    public FirebaseFirestore getDatabase() {
        return database;
    }
}
