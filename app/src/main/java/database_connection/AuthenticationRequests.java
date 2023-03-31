package database_connection;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import others.Manager;

public class AuthenticationRequests {

    public static String registerUser(String email, String username, String password) {
        MongoDatabase database = Manager.getDbConnection().getDatabase();
        MongoCollection<Document> usersCollection = database.getCollection("Users");

        Document newDocument = new Document();
        newDocument.append("email", email);
        newDocument.append("username", username);
        newDocument.append("password", password);

        if(checkUserExists(email, username, password))
            return "User already exists";

        try {
            usersCollection.insertOne(newDocument);
            return "User added successfully";
        } catch (MongoException e) {
            return "Error creating user";
        }
    }

    public static boolean checkUserExists(String email, String username, String password) {
        MongoDatabase database = Manager.getDbConnection().getDatabase();
        MongoCollection<Document> usersCollection = database.getCollection("Users");

        BasicDBObject searchCriteria = new BasicDBObject();
        searchCriteria.append("email", email);
        searchCriteria.append("password", password);
        Document emailSearchDocument = usersCollection.find(searchCriteria).first();

        searchCriteria.remove("email");
        searchCriteria.append("username", username);
        Document usernameSearchDocument = usersCollection.find(searchCriteria).first();

        return emailSearchDocument != null || usernameSearchDocument != null;
    }
}
