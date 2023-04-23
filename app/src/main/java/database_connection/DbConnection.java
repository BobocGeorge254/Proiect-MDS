package database_connection;


import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DbConnection {
    private final FirebaseFirestore database;
    private final FirebaseStorage storage;

    public DbConnection() {
        database = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public FirebaseFirestore getDatabase() {
        return database;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }
}
