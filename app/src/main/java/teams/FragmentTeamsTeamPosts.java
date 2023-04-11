package teams;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.register.R;

import java.util.ArrayList;

import database_connection.TeamsRequests;
import interfaces.ActivityBasics;
import others.PreferencesManager;

public class FragmentTeamsTeamPosts extends Fragment implements ActivityBasics {

    private TextView act_teams_fr_team_posts_team_id_TW;
    private RecyclerView act_teams_fr_team_posts_recycleview;
    private Button act_teams_fr_team_posts_add_chanel_button;
    private LinearLayout act_teams_fr_team_post_create_chanel_window;
    private EditText act_teams_fr_team_post_create_chanel_name_ET;
    private Button act_teams_fr_team_post_create_chanel_create_button;
    private Button act_teams_fr_team_post_create_chanel_cancel_button;
    private View view;
    private AdapterTeamChanelName adapterTeamsChanelName;
    private ArrayList<DataTeamChanelNameCard> dataTeamChanelNameCardList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teams_team_posts, container, false);

        getActivityElements();
        setListeners();

        dataTeamChanelNameCardList = TeamsRequests.getTeamsChanels(PreferencesManager.getLastOpenedTeamId(getContext()));
        setTeamsChanelNamesAdapter();

        act_teams_fr_team_post_create_chanel_window.setVisibility(View.INVISIBLE);

        return view;
    }


    @Override
    public void getActivityElements() {
        act_teams_fr_team_posts_recycleview = view.findViewById(R.id.act_teams_fr_team_posts_recycleview);
        act_teams_fr_team_posts_add_chanel_button = view.findViewById(R.id.act_teams_fr_team_posts_add_chanel_button);
        act_teams_fr_team_post_create_chanel_window = view.findViewById(R.id.act_teams_fr_team_post_create_chanel_window);
        act_teams_fr_team_post_create_chanel_name_ET = view.findViewById(R.id.act_teams_fr_team_post_create_chanel_name_ET);
        act_teams_fr_team_post_create_chanel_create_button = view.findViewById(R.id.act_teams_fr_team_post_create_chanel_create_button);
        act_teams_fr_team_post_create_chanel_cancel_button = view.findViewById(R.id.act_teams_fr_team_post_create_chanel_cancel_button);

        act_teams_fr_team_posts_team_id_TW = view.findViewById(R.id.act_teams_fr_team_posts_team_id_TW);
        act_teams_fr_team_posts_team_id_TW.setText(PreferencesManager.getLastOpenedTeamId(getContext()));
    }

    @Override
    public void setListeners() {
        act_teams_fr_team_posts_add_chanel_button_onClick();
        act_teams_fr_team_post_create_chanel_create_button_onClick();
        act_teams_fr_team_post_create_chanel_cancel_button_onClick();
    }

    private void act_teams_fr_team_posts_add_chanel_button_onClick() {
        act_teams_fr_team_posts_add_chanel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_teams_fr_team_post_create_chanel_window.setVisibility(View.VISIBLE);
            }
        });
    }

    private void act_teams_fr_team_post_create_chanel_create_button_onClick() {
        act_teams_fr_team_post_create_chanel_create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = act_teams_fr_team_post_create_chanel_name_ET.getText().toString().trim();
                String response = TeamsRequests.addChanel(PreferencesManager.getLastOpenedTeamId(getContext()), name);

                if(!response.equals("Error adding chanel")) {
                    dataTeamChanelNameCardList.add(new DataTeamChanelNameCard(response, name));
                    adapterTeamsChanelName.notifyItemInserted(dataTeamChanelNameCardList.size() - 1);
                }
            }
        });
    }

    private void act_teams_fr_team_post_create_chanel_cancel_button_onClick() {
        act_teams_fr_team_post_create_chanel_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_teams_fr_team_post_create_chanel_window.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setTeamsChanelNamesAdapter() {
        adapterTeamsChanelName = new AdapterTeamChanelName(dataTeamChanelNameCardList, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        act_teams_fr_team_posts_recycleview.setLayoutManager(layoutManager);
        act_teams_fr_team_posts_recycleview.setItemAnimator(new DefaultItemAnimator());
        act_teams_fr_team_posts_recycleview.setAdapter(adapterTeamsChanelName);
    }
}