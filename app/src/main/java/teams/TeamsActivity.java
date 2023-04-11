package teams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.register.R;

import authentication.FragmentLogIn;
import interfaces.ActivityBasics;
import profile.ProfileActivity;

public class TeamsActivity extends AppCompatActivity implements ActivityBasics {

    private Button act_teams_fr_listing_menu_nav_team_button;
    private Button act_teams_fr_listing_menu_nav_profile_button;

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
        act_teams_fr_listing_menu_nav_profile_button = findViewById(R.id.act_teams_fr_listing_menu_nav_profile_button);
    }

    @Override
    public void setListeners() {
        act_teams_fr_listing_menu_nav_team_button_onClick();
        act_teams_fr_listing_menu_nav_profile_button_onClick();
    }

    private void act_teams_fr_listing_menu_nav_team_button_onClick() {
        act_teams_fr_listing_menu_nav_team_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTeamsListingFragment();
            }
        });
    }

    private void act_teams_fr_listing_menu_nav_profile_button_onClick() {
        act_teams_fr_listing_menu_nav_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switch_to_profile = new Intent(TeamsActivity.this, ProfileActivity.class) ;
                //String username = act_authentication_fr_register_username_ET.getText().toString().trim();
                //switch_to_profile.putExtra("username", username) ;
                startActivity(switch_to_profile);
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
}