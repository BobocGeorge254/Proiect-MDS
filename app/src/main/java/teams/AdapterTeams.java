package teams;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.R;

import java.util.ArrayList;

public class AdapterTeams extends RecyclerView.Adapter<AdapterTeams.MyViewHolder> {

    private final ArrayList<DataTeamCard> teamsList;
    private Context context;

    public AdapterTeams(ArrayList<DataTeamCard> teamsList, Context context)
    {
        this.teamsList = teamsList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView card_name;
        private final TextView card_description;

        public MyViewHolder(final View view) {
            super(view);
            card_name = view.findViewById(R.id.card_team_name_TW);
            card_description = view.findViewById(R.id.card_team_description_TW);
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

        String description = teamsList.get(position).getDescription();
        holder.card_description.setText(description);
    }

    @Override
    public int getItemCount() {
        return teamsList.size();
    }
}

