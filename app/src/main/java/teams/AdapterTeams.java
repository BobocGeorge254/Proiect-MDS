package teams;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.register.R;

import java.util.ArrayList;

import database_connection.FileRequest;
import database_connection.TeamsRequests;
import others.PreferencesManager;

public class AdapterTeams extends RecyclerView.Adapter<AdapterTeams.MyViewHolder> {

    private final ArrayList<DataTeamCard> teamsList;
    private Context context;

    private OnTeamChanelCardClickListener onTeamChanelCardClickListener;

    private OnTeamCardEditButtonClickListener onTeamCardEditButtonClickListener;

    public void setOnTeamCardEditButtonClickListener(OnTeamCardEditButtonClickListener onTeamCardEditButtonClickListener) {
        this.onTeamCardEditButtonClickListener = onTeamCardEditButtonClickListener;
    }

    public void setOnTeamChanelCardClickListener(OnTeamChanelCardClickListener onTeamChanelCardClickListener) {
        this.onTeamChanelCardClickListener = onTeamChanelCardClickListener;
    }

    public AdapterTeams(ArrayList<DataTeamCard> teamsList, Context context)
    {
        this.teamsList = teamsList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView card_name;
        private final ImageButton card_3dots_button;
        private final ImageView card_logo;
        private final ImageButton card_dropdown_button;
        private final RecyclerView card_chanels_recycleview;
        private AdapterTeamChanelName adapterTeamsChanelName;
        private final ArrayList<DataTeamChanelNameCard> dataTeamChanelNameCardList;

        public MyViewHolder(final View view) {
            super(view);
            card_name = view.findViewById(R.id.card_team_name_TW);
            card_3dots_button = view.findViewById(R.id.card_team_3dots_imageButton);
            card_logo = view.findViewById(R.id.card_team_logo_imageView);
            card_chanels_recycleview = view.findViewById(R.id.card_team_chanels_recycleview);
            card_dropdown_button = view.findViewById(R.id.card_team_dropdown_imageButton);

            dataTeamChanelNameCardList = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public AdapterTeams.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View team_card_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_team, parent, false);
        return new MyViewHolder(team_card_view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTeams.MyViewHolder holder, int position) {
        String name = teamsList.get(position).getName();
        holder.card_name.setText(name);

        String photoUri = teamsList.get(position).getPhotoUri();
        holder.card_logo.setImageBitmap(FileRequest.getTeamLogo(context, photoUri));

        String teamId = teamsList.get(position).getId();

 //       holder.dataTeamChanelNameCardList.add(TeamsRequests.getTeamsChanels(PreferencesManager.getLastOpenedTeamId(context)).get(0));
        holder.dataTeamChanelNameCardList.add(TeamsRequests.getTeamsChanels(teamId).get(0));
        holder.adapterTeamsChanelName = new AdapterTeamChanelName(holder.dataTeamChanelNameCardList, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        holder.card_chanels_recycleview.setLayoutManager(layoutManager);
        holder.card_chanels_recycleview.setItemAnimator(new DefaultItemAnimator());
        holder.card_chanels_recycleview.setAdapter(holder.adapterTeamsChanelName);

        holder.adapterTeamsChanelName.setOnTeamChanelCardClickListener(new OnTeamChanelCardClickListener() {
            @Override
            public void onCardItemClick(String teamChanelId) {
                PreferencesManager.saveLastOpenedTeamId(context, teamId);
                PreferencesManager.saveLastOpenedTeamChanelId(context, teamChanelId);

                if(onTeamChanelCardClickListener != null)
                    onTeamChanelCardClickListener.onCardItemClick(teamId);
            }
        });

        holder.card_dropdown_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int initialChanelsNumber = holder.dataTeamChanelNameCardList.size();
                holder.dataTeamChanelNameCardList.clear();

                if(initialChanelsNumber == 1)  //only the general
                    holder.dataTeamChanelNameCardList.addAll(TeamsRequests.getTeamsChanels(teamId));
                else
                    holder.dataTeamChanelNameCardList.add(TeamsRequests.getTeamsChanels(teamId).get(0));

                holder.adapterTeamsChanelName.notifyDataSetChanged();
            }
        });

        holder.card_3dots_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onTeamCardEditButtonClickListener != null)
                    onTeamCardEditButtonClickListener.onCardItemClick(teamId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamsList.size();
    }
}

