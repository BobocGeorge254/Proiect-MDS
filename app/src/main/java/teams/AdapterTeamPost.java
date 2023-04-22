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

public class AdapterTeamPost extends RecyclerView.Adapter<AdapterTeamPost.MyViewHolder> {

    private final ArrayList<DataTeamPost> teamPostList;
    private Context context;


    public AdapterTeamPost(ArrayList<DataTeamPost> teamPostList, Context context)
    {
        this.teamPostList = teamPostList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView card_sender_name;
        private final TextView card_send_date;
        private final TextView card_text;

        public MyViewHolder(final View view) {
            super(view);
            card_sender_name = view.findViewById(R.id.card_team_post_sender_name_TW);
            card_send_date = view.findViewById(R.id.card_team_post_send_date_TW);
            card_text = view.findViewById(R.id.card_team_post_text_TW);
        }
    }

    @NonNull
    @Override
    public AdapterTeamPost.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View team_post_card_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_team_post, parent, false);
        return new AdapterTeamPost.MyViewHolder(team_post_card_view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTeamPost.MyViewHolder holder, int position) {
        String senderName = teamPostList.get(position).getSenderName();
        holder.card_sender_name.setText(senderName);

        String sendDate = teamPostList.get(position).getSendDate();
        holder.card_send_date.setText(sendDate);

        String text = teamPostList.get(position).getText();
        holder.card_text.setText(text);
    }

    @Override
    public int getItemCount() {
        return teamPostList.size();
    }
}