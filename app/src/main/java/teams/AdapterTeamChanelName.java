package teams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.R;

import java.util.ArrayList;

public class AdapterTeamChanelName extends RecyclerView.Adapter<AdapterTeamChanelName.MyViewHolder> {

    private final ArrayList<DataTeamChanelNameCard> teamChanelNameList;
    private Context context;


    public AdapterTeamChanelName(ArrayList<DataTeamChanelNameCard> teamChanelNameList, Context context)
    {
        this.teamChanelNameList = teamChanelNameList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView card_name;

        public MyViewHolder(final View view) {
            super(view);
            card_name = view.findViewById(R.id.card_team_chanel_name_TW);
        }
    }

    @NonNull
    @Override
    public AdapterTeamChanelName.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View team_chanel_name_card_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_team_chanel_name, parent, false);
        return new AdapterTeamChanelName.MyViewHolder(team_chanel_name_card_view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTeamChanelName.MyViewHolder holder, int position) {
        String name = teamChanelNameList.get(position).getName();
        holder.card_name.setText(name);
    }

    @Override
    public int getItemCount() {
        return teamChanelNameList.size();
    }
}
