package teams;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.register.R;

import java.util.ArrayList;

import database_connection.TeamsRequests;
import interfaces.ActivityBasics;
import others.PreferencesManager;
import profile.ProfileActivity;


public class FragmentTeamsListing extends Fragment implements ActivityBasics {

    private RecyclerView act_teams_fr_listing_recycleview;
    private LinearLayout act_teams_fr_listing_create_team_window;
    private Button act_teams_fr_listing_create_team_button;
    private Button act_teams_fr_listing_join_team_button;
    private EditText act_teams_fr_listing_create_team_card_name;
    private EditText act_teams_fr_listing_create_team_card_description;
    private Button act_teams_fr_listing_create_team_card_create_button;
    private Button act_teams_fr_listing_create_team_card_cancel_button;
    private Button act_teams_frt_listing_mew_nav_profile_button ;
    private LinearLayout act_teams_fr_listing_join_team_window;
    private Button act_teams_fr_listing_join_team_card_join_button;
    private Button act_teams_fr_listing_join_team_card_cancel_button;
    private EditText act_teams_fr_listing_join_team_card_ET;

    private EditText act_authentication_fr_register_username_ET;
    private View view;
    private AdapterTeams adapterTeams;
    private ArrayList<DataTeamCard> dataTeamCardList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_teams_listing, container, false);

        getActivityElements();
        setListeners();

        act_teams_fr_listing_create_team_window.setVisibility(View.INVISIBLE);
        act_teams_fr_listing_join_team_window.setVisibility(View.INVISIBLE);

        dataTeamCardList = TeamsRequests.getTeams(PreferencesManager.getUserId(getContext()));
        setTeamsAdapter();

        return view;
    }

    @Override
    public void getActivityElements() {
        act_teams_fr_listing_recycleview = view.findViewById(R.id.act_teams_fr_listing_recycleview);
        act_teams_fr_listing_create_team_window = view.findViewById(R.id.act_teams_fr_listing_create_team_window);
        act_teams_fr_listing_create_team_button = view.findViewById(R.id.act_teams_fr_listing_create_team_button);
        act_teams_fr_listing_join_team_button = view.findViewById(R.id.act_teams_fr_listing_join_team_button);
        act_teams_fr_listing_create_team_card_name = view.findViewById(R.id.act_teams_fr_listing_create_team_card_name);
        act_teams_fr_listing_create_team_card_description = view.findViewById(R.id.act_teams_fr_listing_create_team_card_description);
        act_teams_fr_listing_create_team_card_create_button = view.findViewById(R.id.act_teams_fr_listing_create_team_card_create_button);
        act_teams_fr_listing_create_team_card_cancel_button = view.findViewById(R.id.act_teams_fr_listing_create_team_card_cancel_button);
        act_teams_fr_listing_join_team_window = view.findViewById(R.id.act_teams_fr_listing_join_team_window);
        act_teams_fr_listing_join_team_card_join_button = view.findViewById(R.id.act_teams_fr_listing_join_team_card_join_button);
        act_teams_fr_listing_join_team_card_cancel_button = view.findViewById(R.id.act_teams_fr_listing_join_team_card_cancel_button);
        act_teams_fr_listing_join_team_card_ET = view.findViewById(R.id.act_teams_fr_listing_join_team_card_ET);
        act_teams_frt_listing_mew_nav_profile_button = view.findViewById(R.id.act_teams_fr_listing_menu_nav_profile_button);
        act_authentication_fr_register_username_ET = view.findViewById(R.id.act_authentication_fr_register_username_ET);
    }

    @Override
    public void setListeners() {
        act_teams_fr_listing_create_team_button_onClick();
        act_teams_fr_listing_join_team_button_onClick();
        act_teams_fr_listing_create_team_card_cancel_button_onClick();
        act_teams_fr_listing_create_team_card_create_button_onClick();
        act_teams_fr_listing_join_team_card_join_button_onClick();
        act_teams_fr_listing_join_team_card_cancel_button_onClick();
        act_teams_fr_listing_menu_nav_profile_button_onClick();
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

    private void act_teams_fr_listing_join_team_button_onClick()
    {
        act_teams_fr_listing_join_team_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_teams_fr_listing_join_team_window.setVisibility(View.VISIBLE);
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

                String response = TeamsRequests.createTeam(teamName, teamDescription, PreferencesManager.getUserId(getContext()));
                System.out.println(response);

                if(!response.equals("Error adding team")) {
                    dataTeamCardList.add(new DataTeamCard(response, teamName, teamDescription));
                    adapterTeams.notifyItemInserted(dataTeamCardList.size() - 1);
                }
            }
        });
    }

    private void act_teams_fr_listing_join_team_card_join_button_onClick()
    {
        act_teams_fr_listing_join_team_card_join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teamCode = act_teams_fr_listing_join_team_card_ET.getText().toString().trim();
                String response = TeamsRequests.addUserToTeam(PreferencesManager.getUserId(getContext()), teamCode, "Member");

                System.out.println(response);
                if(response.equals(teamCode)) {
                    dataTeamCardList.clear();
                    dataTeamCardList.addAll(TeamsRequests.getTeams(PreferencesManager.getUserId(getContext())));
                    adapterTeams.notifyDataSetChanged();
                }
            }
        });
    }

    private void act_teams_fr_listing_join_team_card_cancel_button_onClick()
    {
        act_teams_fr_listing_join_team_card_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_teams_fr_listing_join_team_window.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void act_teams_fr_listing_menu_nav_profile_button_onClick() {
        act_teams_frt_listing_mew_nav_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switch_to_profile = new Intent(getContext(), ProfileActivity.class) ;
                //String username = act_authentication_fr_register_username_ET.getText().toString().trim();
                //switch_to_profile.putExtra("username", username) ;
                startActivity(switch_to_profile);
            }
        });
    }

    private void setTeamsAdapter()
    {
        adapterTeams = new AdapterTeams(dataTeamCardList, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        act_teams_fr_listing_recycleview.setLayoutManager(layoutManager);
        act_teams_fr_listing_recycleview.setItemAnimator(new DefaultItemAnimator());
        act_teams_fr_listing_recycleview.setAdapter(adapterTeams);
    }
}