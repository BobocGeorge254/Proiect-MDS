package teams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.R;

import java.util.ArrayList;

import database_connection.TeamsRequests;
import others.PreferencesManager;

public class AdapterTeamPostReply extends RecyclerView.Adapter<AdapterTeamPostReply.MyViewHolder> {

    private final ArrayList<DataTeamPostReply> teamPostRepliesList;
    private Context context;

    private OnTeamPostReplyDeleteClickListener onTeamPostReplyDeleteClickListener;

    public void setOnTeamPostReplyDeleteClickListener(OnTeamPostReplyDeleteClickListener onTeamPostReplyDeleteClickListener) {
        this.onTeamPostReplyDeleteClickListener = onTeamPostReplyDeleteClickListener;
    }

    public AdapterTeamPostReply(ArrayList<DataTeamPostReply> teamPostRepliesList, Context context)
    {
        this.teamPostRepliesList = teamPostRepliesList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView card_sender_name;
        private final TextView card_send_date;
        private final TextView card_text;
        private final ImageButton card_delete;

        public MyViewHolder(final View view) {
            super(view);
            card_sender_name = view.findViewById(R.id.card_team_post_reply_sender_name_TW);
            card_send_date = view.findViewById(R.id.card_team_post_reply_send_date_TW);
            card_text = view.findViewById(R.id.card_team_post_reply_text_TW);
            card_delete = view.findViewById(R.id.card_team_post_reply_delete_button);
        }
    }

    @NonNull
    @Override
    public AdapterTeamPostReply.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View team_post_reply_card_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_team_post_reply, parent, false);
        return new AdapterTeamPostReply.MyViewHolder(team_post_reply_card_view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTeamPostReply.MyViewHolder holder, int position) {
        String senderName = teamPostRepliesList.get(position).getSenderName();
        holder.card_sender_name.setText(senderName);

        String sendDate = teamPostRepliesList.get(position).getSendDate();
        holder.card_send_date.setText(sendDate);

        String text = teamPostRepliesList.get(position).getText();
        holder.card_text.setText(text);

        String teamPostId = teamPostRepliesList.get(position).getId();

        holder.card_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTeamPostReplyDeleteClickListener.onCardItemClick(teamPostId);
            }
        });

        if(!(TeamsRequests.getTeamRole(PreferencesManager.getUserId(context), teamPostRepliesList.get(position).getTeamId()).equals("Admin") ||
                teamPostRepliesList.get(position).getSenderId().equals(PreferencesManager.getUserId(context))))
            holder.card_delete.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return teamPostRepliesList.size();
    }
}
