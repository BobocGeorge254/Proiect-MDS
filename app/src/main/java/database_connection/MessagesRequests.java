package database_connection;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import chat.DataMessageCard;
import chat.DataUserCard;
import others.Manager;

public class MessagesRequests {

    public static DataUserCard getDataUser(String id) {
        CollectionReference usersCollection = Manager.dbConnection.getDatabase().collection("Users");
        Task<DocumentSnapshot> getDataUser = usersCollection.document(id).get();

        while (!getDataUser.isComplete()) {}  //blocks until query is executed

        String email =  getDataUser.getResult().getString("email");
        String username =  getDataUser.getResult().getString("username");
        String password = getDataUser.getResult().getString("password") ;
        return new DataUserCard(id, email, username, password);

    }

    public static Set<String> getUsersFriends(String userId) {
        CollectionReference messagesCollection = Manager.dbConnection.getDatabase().collection("Messages");
        CollectionReference usersCollection = Manager.dbConnection.getDatabase().collection("Users");

        Set<String> usersMessagesList = new HashSet<>();

        //If current user is sender
        Query queryGetFriend = messagesCollection.whereEqualTo("sender_id", userId);
        Task<QuerySnapshot> queryGetFriendTask = queryGetFriend.get();

        while (!queryGetFriendTask.isComplete()) {
        }   //blocks until query is executed

        for (DocumentSnapshot documentSnapshot : queryGetFriendTask.getResult().getDocuments()) {
            String id_friend = documentSnapshot.getString("receiver_id");
            usersMessagesList.add(id_friend);
        }

        //If current user is receiver
        queryGetFriend = messagesCollection.whereEqualTo("receiver_id", userId);
        queryGetFriendTask = queryGetFriend.get();

        while (!queryGetFriendTask.isComplete()) {
        }

        for (DocumentSnapshot documentSnapshot1 : queryGetFriendTask.getResult().getDocuments()) {
            String id_friend1 = documentSnapshot1.getString("sender_id");
            usersMessagesList.add(id_friend1);
        }
    return usersMessagesList;
    }

    public static ArrayList<DataUserCard> SearchByUsername (String username) {

        ArrayList<DataUserCard> usersList = new ArrayList<>();
        CollectionReference usersCollection = Manager.dbConnection.getDatabase().collection("Users");

        Query queryGetUser = usersCollection.whereEqualTo("username", username);
        Task<QuerySnapshot> queryGetUsersTask = queryGetUser.get();

        while (!queryGetUsersTask.isComplete()) {
        }   //blocks until query is executed

        if (!queryGetUsersTask.getResult().isEmpty()) {
            for (DocumentSnapshot documentSnapshot : queryGetUsersTask.getResult().getDocuments()) {
                String id = documentSnapshot.getId();
                String email_user = documentSnapshot.getString("email");
                String username_user = documentSnapshot.getString("username");
                String password_user = documentSnapshot.getString("password") ;
                DataUserCard user = new DataUserCard(id,email_user, username_user, password_user);

                usersList.add(user);
            }
        }

        return usersList;

    }

    public static ArrayList<DataUserCard> SearchByEmail (String email) {

        ArrayList<DataUserCard> usersList = new ArrayList<>();
        CollectionReference usersCollection = Manager.dbConnection.getDatabase().collection("Users");

        Query queryGetUser = usersCollection.whereEqualTo("email", email);
        Task<QuerySnapshot> queryGetUsersTask = queryGetUser.get();

        while (!queryGetUsersTask.isComplete()) {
        }   //blocks until query is executed

        if (!queryGetUsersTask.getResult().isEmpty()) {
            for (DocumentSnapshot documentSnapshot : queryGetUsersTask.getResult().getDocuments()) {
                String id = documentSnapshot.getId();
                String email_user = documentSnapshot.getString("email");
                String username_user = documentSnapshot.getString("username");
                String password_user = documentSnapshot.getString("password") ;
                DataUserCard user = new DataUserCard(id,email_user, username_user, password_user);

                usersList.add(user);
            }
        }

        return usersList;

    }

    public static ArrayList<DataMessageCard> GetUserMessages(String currentUserId, String friendId) {
        ArrayList<DataMessageCard> messagesList = new ArrayList<>();

        CollectionReference messagesCollection = Manager.dbConnection.getDatabase().collection("Messages");

        Query queryGetMessages = messagesCollection.whereEqualTo("sender_id", currentUserId);
        Task<QuerySnapshot> queryGetMessagesTask = queryGetMessages.get();

        while (!queryGetMessagesTask.isComplete()) {
        }   //blocks until query is executed


        for (DocumentSnapshot documentSnapshot : queryGetMessagesTask.getResult().getDocuments()) {
            if(Objects.equals(documentSnapshot.getString("receiver_id"), friendId)) {
                String id = documentSnapshot.getId();
                String sender_id = documentSnapshot.getString("sender_id");
                String receiver_id = documentSnapshot.getString("receiver_id");
                String senderUsername = getUsername(sender_id);
                String receiverUsername = getUsername(receiver_id);
                String text = documentSnapshot.getString("text");
                String datePosted = documentSnapshot.getString("datePosted");
                System.out.println(datePosted + "Dateposted MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");

                messagesList.add(new DataMessageCard(senderUsername, receiverUsername, sender_id, receiver_id, id, datePosted, text));
            }
        }

        Query queryGetMessages1 = messagesCollection.whereEqualTo("receiver_id", currentUserId);
        Task<QuerySnapshot> queryGetMessagesTask1 = queryGetMessages1.get();

        while (!queryGetMessagesTask1.isComplete()) {
        }   //blocks until query is executed


        for (DocumentSnapshot documentSnapshot : queryGetMessagesTask1.getResult().getDocuments()) {
            if(Objects.equals(documentSnapshot.getString("sender_id"), friendId)) {
                String id = documentSnapshot.getId();
                String sender_id = documentSnapshot.getString("sender_id");
                String receiver_id = documentSnapshot.getString("receiver_id");
                String senderUsername = getUsername(sender_id);
                String receiverUsername = getUsername(receiver_id);
                String text = documentSnapshot.getString("text");
                String datePosted = documentSnapshot.getString("datePosted");

                messagesList.add(new DataMessageCard(senderUsername, receiverUsername, sender_id, receiver_id, id, datePosted, text));
            }
        }
        return messagesList;
    }
    public static String getUsername(String id) {
        CollectionReference usersCollection = Manager.dbConnection.getDatabase().collection("Users");
        Task<DocumentSnapshot> getDataUser = usersCollection.document(id).get();

        while (!getDataUser.isComplete()) {}  //blocks until query is executed

        String username =  getDataUser.getResult().getString("username");
        return username;
    }
}