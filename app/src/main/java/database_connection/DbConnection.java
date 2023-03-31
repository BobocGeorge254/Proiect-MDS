package database_connection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


public class DbConnection {

    private final String connectionString;
    private final MongoDatabase database;

    public DbConnection() {
        connectionString = "mongodb+srv://testUser:testPass@cards.ckq39bj.mongodb.net/?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(connectionString);
        database = mongoClient.getDatabase("Proiect_PAO");
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
