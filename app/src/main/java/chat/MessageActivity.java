package chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.register.R;

import java.util.ArrayList;
import java.util.Set;

import database_connection.MessagesRequests;
import interfaces.ActivityBasics;
import others.PreferencesManager;

public class MessageActivity extends AppCompatActivity implements ActivityBasics, OnUserCardOpenButtonClickListener {

    private RecyclerView act_chat_message_recyclerView;
    private RecyclerView act_chat_recyclerView;
    private Button act_chat_search_button;
    private RadioGroup act_chat_radio_group;
    private RadioButton act_chat_searchByEmail_button;
    private RadioButton act_chat_searchByUsername_button;
    private Boolean isSearchByEmail;
    private EditText textbox_search;
    private AdapterMessages adapterMessages;
    private AdapterUsers adapterUsers;
    private ArrayList<DataUserCard> searchList = new ArrayList<>();
    private ArrayList<DataUserCard> usersList = new ArrayList<>();
    private ArrayList<DataMessageCard> messagesColletion = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getActivityElements();

        Set<String> usersMessageCollection;
        usersMessageCollection = MessagesRequests.getUsersFriends(PreferencesManager.getUserId(MessageActivity.this));
        for (String userId : usersMessageCollection) {
            // Pentru fiecare utilizator cu care s-a discutat, adaugă un obiect DataUserCard în lista de utilizatori
            DataUserCard userCard = MessagesRequests.getDataUser(userId);
            usersList.add(userCard);
        }

        setUserAdapter(usersList);

        act_chat_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Verificăm care buton a fost selectat și actualizăm variabila corespunzătoare
                if (checkedId == act_chat_searchByEmail_button.getId()) {
                    isSearchByEmail = true;
                } else if (checkedId == act_chat_searchByUsername_button.getId()) {
                    isSearchByEmail = false;
                }
            }
        });

        act_chat_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("MERGEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                // Obținem textul introdus în EditText
                String searchText = textbox_search.getText().toString();

                // Verificăm care opțiune a fost selectată și efectuăm căutarea corespunzătoare
                if (isSearchByEmail) {
                    searchList.clear();
                    searchList.addAll(MessagesRequests.SearchByEmail(searchText));
                } else {
                    searchList.clear();
                    searchList.addAll(MessagesRequests.SearchByUsername(searchText));
                }

                for(DataUserCard it: searchList) {
                    System.out.println(it.getUsername());
                }

//                adapterUsers.notifyDataSetChanged();
                setUserAdapter(searchList);
            }
        });
    }

    @Override
    public void getActivityElements() {
        act_chat_message_recyclerView = findViewById(R.id.recycler_view_users);
        act_chat_recyclerView = findViewById(R.id.recycler_view_messages);
        act_chat_radio_group = findViewById(R.id.act_chat_radio_group);
        act_chat_searchByEmail_button = findViewById(R.id.act_chat_searchByEmail_button);
        act_chat_searchByUsername_button = findViewById(R.id.act_chat_searchByUsername_button);
        act_chat_search_button = findViewById(R.id.act_chat_search_button);
        textbox_search = findViewById(R.id.textbox_search);

    }

    @Override
    public void onCardUserClick(String currentUserId, String friendId) {
    }

    @Override
    public void setListeners() {
    }

    private void setUserAdapter(ArrayList<DataUserCard> list) {
        adapterUsers = new AdapterUsers(PreferencesManager.getUserId(MessageActivity.this), list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        act_chat_message_recyclerView.setLayoutManager(layoutManager);
        act_chat_message_recyclerView.setItemAnimator(new DefaultItemAnimator());
        act_chat_message_recyclerView.setAdapter(adapterUsers);

        adapterUsers.setOnUserCardOpenButtonClickListener(new OnUserCardOpenButtonClickListener() {
                @Override
                public void onCardUserClick(String currentUserId, String friendId) {
                    Intent intent = new Intent(act_chat_message_recyclerView.getContext(), MessageDetailActivity.class);
                    intent.putExtra("currentUserId", currentUserId);
                    intent.putExtra("friendId", friendId);
                    startActivity(intent);
                }
        });
    }

    private void setMesssageAdapter() {

        adapterMessages = new AdapterMessages(messagesColletion, this);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        act_chat_recyclerView.setLayoutManager(layoutManager2);
        act_chat_recyclerView.setItemAnimator(new DefaultItemAnimator());
        act_chat_recyclerView.setAdapter(adapterMessages);

    }


}