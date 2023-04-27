package database_connection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chat.DataUserCard;
import others.Manager;
import teams.DataTeamCard;
import teams.DataTeamChanelNameCard;

public class MessagesRequests {

    public static DataUserCard getDataUser(String id) {
        CollectionReference usersCollection = Manager.dbConnection.getDatabase().collection("Users");
        Task<DocumentSnapshot> getDataUser = usersCollection.document(id).get();

        while (!getDataUser.isComplete()) {}  //blocks until query is executed

        return (DataUserCard) getDataUser.getResult().get(id);

    }

    public static Set<String> getUsersMessage(String userId) {
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

//            Task<DocumentSnapshot> getUserTask = usersCollection.document(id_friend).get();
//            while (!getUserTask.isComplete()) {
//            }  //blocks until query is executed
//
//            if (getUserTask.getResult().exists()) {
//                String name = getUserTask.getResult().get("username").toString();
//                usersMessagesList.add(name);
//            }
        }

        //If current user is receiver
        queryGetFriend = messagesCollection.whereEqualTo("receiver_id", userId);
        queryGetFriendTask = queryGetFriend.get();

        while (!queryGetFriendTask.isComplete()) {
        }

        for (DocumentSnapshot documentSnapshot1 : queryGetFriendTask.getResult().getDocuments()) {
            String id_friend1 = documentSnapshot1.getString("sender_id");
            usersMessagesList.add(id_friend1);

//            Task<DocumentSnapshot> getUserTask1 = usersCollection.document(id_friend1).get();
//
//            while (!getUserTask1.isComplete()) {}  //blocks until query is executed
//
//            if (getUserTask1.getResult().exists()) {
//                String name = getUserTask1.getResult().get("username").toString();
//                usersMessagesList.add(name);
//            }

        }
    return usersMessagesList;
    }

}
