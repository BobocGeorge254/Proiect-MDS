package teams;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.register.R;

import database_connection.TeamsRequests;
import interfaces.ActivityBasics;
import others.PreferencesManager;


public class FragmentTeamsListing extends Fragment implements ActivityBasics {

    LinearLayout act_teams_fr_listing_create_team_window;
    Button act_teams_fr_listing_create_team_button;
    EditText act_teams_fr_listing_create_team_card_name;
    EditText act_teams_fr_listing_create_team_card_description;
    Button act_teams_fr_listing_create_team_card_create_button;
    Button act_teams_fr_listing_create_team_card_cancel_button;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_teams_listing, container, false);

        getActivityElements();
        setListeners();

        act_teams_fr_listing_create_team_window.setVisibility(View.INVISIBLE);

        System.out.println("Teams list: ");
        for(String team : TeamsRequests.getTeamsIds(PreferencesManager.getUserId(getContext())))
            System.out.println(team);

        return view;
    }

    @Override
    public void getActivityElements() {
        act_teams_fr_listing_create_team_window = view.findViewById(R.id.act_teams_fr_listing_create_team_window);
        act_teams_fr_listing_create_team_button = view.findViewById(R.id.act_teams_fr_listing_create_team_button);
        act_teams_fr_listing_create_team_card_name = view.findViewById(R.id.act_teams_fr_listing_create_team_card_name);
        act_teams_fr_listing_create_team_card_description = view.findViewById(R.id.act_teams_fr_listing_create_team_card_description);
        act_teams_fr_listing_create_team_card_create_button = view.findViewById(R.id.act_teams_fr_listing_create_team_card_create_button);
        act_teams_fr_listing_create_team_card_cancel_button = view.findViewById(R.id.act_teams_fr_listing_create_team_card_cancel_button);
    }

    @Override
    public void setListeners() {
        act_teams_fr_listing_create_team_button_onClick();
        act_teams_fr_listing_create_team_card_cancel_button_onClick();
        act_teams_fr_listing_create_team_card_create_button_onClick();
    }

    private void act_teams_fr_listing_create_team_button_onClick()
    {
        act_teams_fr_listing_create_team_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_teams_fr_listing_create_team_window.setVisibility(View.VISIBLE);
            }
        });
    }

    private void act_teams_fr_listing_create_team_card_cancel_button_onClick()
    {
        act_teams_fr_listing_create_team_card_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_teams_fr_listing_create_team_window.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void act_teams_fr_listing_create_team_card_create_button_onClick()
    {
        act_teams_fr_listing_create_team_card_create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teamName = act_teams_fr_listing_create_team_card_name.getText().toString().trim();
                String teamDescription = act_teams_fr_listing_create_team_card_description.getText().toString().trim();

                String response = TeamsRequests.createTeam(teamName, teamDescription);
                System.out.println(response);

                if(!response.equals("Error adding team")) {
                    String response2 = TeamsRequests.addUserToTeam(PreferencesManager.getUserId(getContext()), response);
                    System.out.println(response2);
                }
            }
        });
    }
}