package chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.register.R;

import java.util.ArrayList;
import java.util.Set;

import database_connection.MessagesRequests;
import interfaces.ActivityBasics;
import others.PreferencesManager;
import teams.AdapterTeamChanelName;
import teams.TeamsActivity;

public class MessageActivity extends AppCompatActivity implements ActivityBasics {

    private RecyclerView act_chat_message_recyclerView;
    private AdapterUsers adapterUsers;
    private ArrayList<DataUserCard> usersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getActivityElements();
        setListeners();

        Set<String> usersMessageCollection;
        usersMessageCollection = MessagesRequests.getUsersMessage(PreferencesManager.getUserId(MessageActivity.this));
        for (String userId : usersMessageCollection) {
            // Pentru fiecare utilizator cu care s-a discutat, adaugă un obiect DataUserCard în lista de utilizatori
            DataUserCard userCard = MessagesRequests.getDataUser(userId);
            usersList.add(userCard);
        }

        adapterUsers = new AdapterUsers(usersList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        act_chat_message_recyclerView.setLayoutManager(layoutManager);
        act_chat_message_recyclerView.setItemAnimator(new DefaultItemAnimator());
//        adapterUsers.setOnUserCardOpenButtonClickListener(this);
        act_chat_message_recyclerView.setAdapter(adapterUsers);

    }

    @Override
    public void getActivityElements() {
        act_chat_message_recyclerView = findViewById(R.id.recycler_view_users);
    }

    @Override
    public void setListeners() {
    }

    private void setUserAdapter() {
        adapterUsers = new AdapterUsers(usersList, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        act_chat_message_recyclerView.setLayoutManager(layoutManager);
        act_chat_message_recyclerView.setItemAnimator(new DefaultItemAnimator());
        act_chat_message_recyclerView.setAdapter(adapterUsers);
    }

}