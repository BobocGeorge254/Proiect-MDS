package teams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.R;

import java.util.ArrayList;

public class AdapterTeamPostReply extends RecyclerView.Adapter<AdapterTeamPostReply.MyViewHolder> {

    private final ArrayList<DataTeamPostReply> teamPostRepliesList;
    private Context context;


    public AdapterTeamPostReply(ArrayList<DataTeamPostReply> teamPostRepliesList, Context context)
    {
        this.teamPostRepliesList = teamPostRepliesList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView card_sender_name;
        private final TextView card_send_date;
        private final TextView card_text;

        public MyViewHolder(final View view) {
            super(view);
            card_sender_name = view.findViewById(R.id.card_team_post_reply_sender_name_TW);
            card_send_date = view.findViewById(R.id.card_team_post_reply_send_date_TW);
            card_text = view.findViewById(R.id.card_team_post_reply_text_TW);
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
    }

    @Override
    public int getItemCount() {
        return teamPostRepliesList.size();
    }
}
