package teams;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.register.R;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import database_connection.OtherRequests;
import database_connection.TeamsRequests;
import interfaces.ActivityBasics;
import others.PreferencesManager;

public class FragmentTeamsTeamPosts extends Fragment implements ActivityBasics {

    private TextView act_teams_fr_team_posts_team_chanel_name_TW;
    private RecyclerView act_teams_fr_team_posts_posts_listing_recycleview;
    private LinearLayout act_teams_fr_team_posts_add_chanel_button;
    private FrameLayout act_teams_fr_team_posts_add_post_button;
    private LinearLayout act_teams_fr_team_post_create_chanel_window;
    private EditText act_teams_fr_team_post_create_chanel_name_ET;
    private TextView act_teams_fr_team_post_create_chanel_create_button;
    private TextView act_teams_fr_team_post_create_chanel_cancel_button;
    private LinearLayout act_teams_fr_team_post_create_post_window;
    private EditText act_teams_fr_team_post_create_post_text_ET;
    private TextView act_teams_fr_team_post_create_post_create_button;
    private TextView act_teams_fr_team_post_create_post_cancel_button;
    private LinearLayout act_teams_fr_team_post_create_reply_window;
    private EditText act_teams_fr_team_post_create_reply_text_ET;
    private TextView act_teams_fr_team_post_create_reply_create_button;
    private TextView act_teams_fr_team_post_create_reply_cancel_button;
    private Button act_teams_fr_team_posts_nav_files_button;
    private View view;
    private AdapterTeamPost adapterTeamsPosts;
    private ArrayList<DataTeamPost> dataTeamPostsList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teams_team_posts, container, false);

        getActivityElements();
        setListeners();

        dataTeamPostsList = TeamsRequests.getTeamsPosts(PreferencesManager.getLastOpenedTeamChanelId(getContext()));
        setTeamsPostsAdapter();

        act_teams_fr_team_post_create_chanel_window.setVisibility(View.INVISIBLE);
        act_teams_fr_team_post_create_post_window.setVisibility(View.INVISIBLE);
        act_teams_fr_team_post_create_reply_window.setVisibility(View.INVISIBLE);
        System.out.println();
        return view;
    }


    @Override
    public void getActivityElements() {
        act_teams_fr_team_posts_posts_listing_recycleview = view.findViewById(R.id.act_teams_fr_team_posts_posts_listing_recycleview);
        act_teams_fr_team_posts_add_chanel_button = view.findViewById(R.id.act_teams_fr_team_posts_add_chanel_button);
        act_teams_fr_team_posts_add_post_button = view.findViewById(R.id.act_teams_fr_team_posts_add_post_button);
        act_teams_fr_team_post_create_chanel_window = view.findViewById(R.id.act_teams_fr_team_post_create_chanel_window);
        act_teams_fr_team_post_create_chanel_name_ET = view.findViewById(R.id.act_teams_fr_team_post_create_chanel_name_ET);
        act_teams_fr_team_post_create_chanel_create_button = view.findViewById(R.id.act_teams_fr_team_post_create_chanel_create_button);
        act_teams_fr_team_post_create_chanel_cancel_button = view.findViewById(R.id.act_teams_fr_team_post_create_chanel_cancel_button);
        act_teams_fr_team_post_create_post_window = view.findViewById(R.id.act_teams_fr_team_post_create_post_window);
        act_teams_fr_team_post_create_post_text_ET = view.findViewById(R.id.act_teams_fr_team_post_create_post_text_ET);
        act_teams_fr_team_post_create_post_create_button = view.findViewById(R.id.act_teams_fr_team_post_create_post_create_button);
        act_teams_fr_team_post_create_post_cancel_button = view.findViewById(R.id.act_teams_fr_team_post_create_post_cancel_button);
        act_teams_fr_team_post_create_reply_window = view.findViewById(R.id.act_teams_fr_team_post_create_reply_window);
        act_teams_fr_team_post_create_reply_text_ET = view.findViewById(R.id.act_teams_fr_team_post_create_reply_text_ET);
        act_teams_fr_team_post_create_reply_create_button = view.findViewById(R.id.act_teams_fr_team_post_create_reply_create_button);
        act_teams_fr_team_post_create_reply_cancel_button = view.findViewById(R.id.act_teams_fr_team_post_create_reply_cancel_button);
        act_teams_fr_team_posts_nav_files_button = view.findViewById(R.id.act_teams_fr_team_posts_nav_files_button);


        act_teams_fr_team_posts_team_chanel_name_TW = view.findViewById(R.id.act_teams_fr_team_posts_team_chanel_name_TW);
        String teamChanelName = TeamsRequests.getTeamChanelData(PreferencesManager.getLastOpenedTeamChanelId(getContext())).getName();
        act_teams_fr_team_posts_team_chanel_name_TW.setText(teamChanelName);
    }

    @Override
    public void setListeners() {
        act_teams_fr_team_posts_add_chanel_button_onClick();
        act_teams_fr_team_post_create_chanel_create_button_onClick();
        act_teams_fr_team_post_create_chanel_cancel_button_onClick();
        act_teams_fr_team_posts_add_post_button_onClick();
        act_teams_fr_team_post_create_post_create_button_onClick();
        act_teams_fr_team_post_create_post_cancel_button_onClick();
        act_teams_fr_team_post_create_reply_create_button_onClick();
        act_teams_fr_team_post_create_reply_cancel_button_onClick();
        act_teams_fr_team_posts_nav_files_button_onClick();
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
                act_teams_fr_team_post_create_chanel_window.setVisibility(View.INVISIBLE);
                if(!response.equals("Error adding chanel")) {
                    //TODO
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

    private void act_teams_fr_team_posts_add_post_button_onClick() {
        act_teams_fr_team_posts_add_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_teams_fr_team_post_create_post_window.setVisibility(View.VISIBLE);
            }
        });
    }

    private void act_teams_fr_team_post_create_post_create_button_onClick() {
        act_teams_fr_team_post_create_post_create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = act_teams_fr_team_post_create_post_text_ET.getText().toString().trim();
                String response = TeamsRequests.addTeamPost(PreferencesManager.getUserId(getContext()), PreferencesManager.getLastOpenedTeamChanelId(getContext()),
                        PreferencesManager.getLastOpenedTeamId(getContext()), text);

                if(!response.equals("Error adding team post")) {
                    String senderName = OtherRequests.getUsernameByUserId(PreferencesManager.getUserId(getContext()));
                    dataTeamPostsList.add(new DataTeamPost(response, senderName, text, LocalDate.now().toString(), PreferencesManager.getUserId(getContext()),
                            PreferencesManager.getLastOpenedTeamId(getContext())));
                    adapterTeamsPosts.notifyItemInserted(dataTeamPostsList.size() - 1);
                }

                act_teams_fr_team_post_create_post_window.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void act_teams_fr_team_post_create_post_cancel_button_onClick() {
        act_teams_fr_team_post_create_post_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_teams_fr_team_post_create_post_window.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void act_teams_fr_team_post_create_reply_create_button_onClick() {
        act_teams_fr_team_post_create_reply_create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = act_teams_fr_team_post_create_reply_text_ET.getText().toString().trim();
                String response = TeamsRequests.addTeamPostReply(PreferencesManager.getUserId(getContext()), PreferencesManager.getLastOpenedTeamPostReplyingId(getContext()),
                        PreferencesManager.getLastOpenedTeamChanelId(getContext()), PreferencesManager.getLastOpenedTeamId(getContext()), text);

                if(!response.equals("Error adding team post reply")) {
                   adapterTeamsPosts.notifyReplyDataChanged(PreferencesManager.getLastOpenedTeamPostReplyingId(getContext()));
                }

                act_teams_fr_team_post_create_reply_window.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void act_teams_fr_team_post_create_reply_cancel_button_onClick() {
        act_teams_fr_team_post_create_reply_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_teams_fr_team_post_create_reply_window.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void act_teams_fr_team_posts_nav_files_button_onClick() {
        act_teams_fr_team_posts_nav_files_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTeamFilesListingFragment();
            }
        });
    }

    private void setTeamsPostsAdapter() {
        adapterTeamsPosts = new AdapterTeamPost(dataTeamPostsList, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        act_teams_fr_team_posts_posts_listing_recycleview.setLayoutManager(layoutManager);
        act_teams_fr_team_posts_posts_listing_recycleview.setItemAnimator(new DefaultItemAnimator());
        act_teams_fr_team_posts_posts_listing_recycleview.setAdapter(adapterTeamsPosts);

        adapterTeamsPosts.setOnTeamPostReplyButtonClickListener(new OnTeamPostReplyButtonClickListener() {
            @Override
            public void onCardItemClick(String teamPostId) {
                PreferencesManager.saveLastOpenedTeamPostReplyingId(getContext(), teamPostId);
                act_teams_fr_team_post_create_reply_window.setVisibility(View.VISIBLE);
            }
        });

        adapterTeamsPosts.setOnTeamPostDeleteClickListener(new OnTeamPostDeleteClickListener() {
            @Override
            public void onCardItemClick(String teamPostId) {
                String response = TeamsRequests.deleteTeamPost(teamPostId);

                if(response.equals("Post deleted successfully")) {
                    dataTeamPostsList.clear();
                    dataTeamPostsList.addAll(TeamsRequests.getTeamsPosts(PreferencesManager.getLastOpenedTeamChanelId(getContext())));
                    adapterTeamsPosts.notifyDataSetChanged();
                    adapterTeamsPosts.notifyReplyDataChanged(teamPostId);
                }
            }
        });
    }

    private void setTeamFilesListingFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_teams_frameLayout, new FragmentTeamsFilesList());
        fragmentTransaction.commit();
    }
}