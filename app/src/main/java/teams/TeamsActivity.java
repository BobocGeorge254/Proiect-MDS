package teams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.register.R;

import java.util.Set;

import authentication.FragmentLogIn;
import chat.MessageActivity;
import database_connection.MessagesRequests;
import interfaces.ActivityBasics;
import others.PreferencesManager;

public class TeamsActivity extends AppCompatActivity implements ActivityBasics {

    private Button act_teams_fr_listing_menu_nav_team_button;
    private Button act_teams_fr_messages_button;
//    TextView MyLenghtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        getActivityElements();
        setListeners();

        setTeamsListingFragment();
    }

    @Override
    public void getActivityElements() {
        act_teams_fr_listing_menu_nav_team_button = findViewById(R.id.act_teams_fr_listing_menu_nav_team_button);
        act_teams_fr_messages_button = findViewById(R.id.act_teams_fr_messages_button);
//        MyLenghtText = findViewById(R.id.test_message);
    }

    @Override
    public void setListeners() {
        act_teams_fr_listing_menu_nav_team_button_onClick();
        act_teams_fr_messages_button_onClick();

    }

    private void act_teams_fr_listing_menu_nav_team_button_onClick() {
        act_teams_fr_listing_menu_nav_team_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTeamsListingFragment();
            }
        });
    }

    private void setTeamsListingFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_teams_frameLayout, new FragmentTeamsListing());
        fragmentTransaction.commit();
    }

    private void act_teams_fr_messages_button_onClick() {
        act_teams_fr_messages_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeamsActivity.this, MessageActivity.class));
            }
        });
    }
}