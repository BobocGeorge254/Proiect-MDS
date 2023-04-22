package teams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.R;

import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import database_connection.TeamsRequests;

public class AdapterTeamPost extends RecyclerView.Adapter<AdapterTeamPost.MyViewHolder> {

    private final ArrayList<DataTeamPost> teamPostList;
    private Context context;
    private List<MyViewHolder> holderList = new ArrayList<>();

    private OnTeamPostReplyButtonClickListener onTeamPostReplyButtonClickListener;

    public void setOnTeamPostReplyButtonClickListener(OnTeamPostReplyButtonClickListener onTeamPostReplyButtonClickListener) {
        this.onTeamPostReplyButtonClickListener = onTeamPostReplyButtonClickListener;
    }


    public AdapterTeamPost(ArrayList<DataTeamPost> teamPostList, Context context) {
        this.teamPostList = teamPostList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView card_sender_name;
        private final TextView card_send_date;
        private final TextView card_text;
        private final TextView card_show_replies;
        private final TextView card_create_reply;
        private final RecyclerView card_replies_recycleview;

        private final ArrayList<DataTeamPostReply> dataTeamPostsRepliesList;
        private AdapterTeamPostReply adapterTeamsPostsReplies;
        private String teamPostId;      //used for teamPostReply update on reply added

        public MyViewHolder(final View view) {
            super(view);
            card_sender_name = view.findViewById(R.id.card_team_post_sender_name_TW);
            card_send_date = view.findViewById(R.id.card_team_post_send_date_TW);
            card_text = view.findViewById(R.id.card_team_post_text_TW);
            card_show_replies = view.findViewById(R.id.card_team_post_show_replies_TW);
            card_create_reply = view.findViewById(R.id.card_team_post_create_reply_TW);
            card_replies_recycleview = view.findViewById(R.id.card_team_post_replies_recycleview);

            dataTeamPostsRepliesList = new ArrayList<>();
            teamPostId = "";
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
        holderList.add(holder);
        holder.teamPostId = teamPostList.get(position).getId();

        String senderName = teamPostList.get(position).getSenderName();
        holder.card_sender_name.setText(senderName);

        String sendDate = teamPostList.get(position).getSendDate();
        holder.card_send_date.setText(sendDate);

        String text = teamPostList.get(position).getText();
        holder.card_text.setText(text);

        String teamPostId = teamPostList.get(position).getId();

        int repliesNumber = TeamsRequests.getTeamsPostsReplies(teamPostId).size();
        String showReplies = "";
        if (repliesNumber == 0)
            showReplies = "There are no replies";
        else
            showReplies = "Show " + repliesNumber + " replies";
        holder.card_show_replies.setText(showReplies);


        holder.adapterTeamsPostsReplies = new AdapterTeamPostReply(holder.dataTeamPostsRepliesList, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        holder.card_replies_recycleview.setLayoutManager(layoutManager);
        holder.card_replies_recycleview.setItemAnimator(new DefaultItemAnimator());
        holder.card_replies_recycleview.setAdapter(holder.adapterTeamsPostsReplies);

        holder.card_create_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTeamPostReplyButtonClickListener != null)
                    onTeamPostReplyButtonClickListener.onCardItemClick(teamPostId);  //creates new reply
            }
        });

        holder.card_show_replies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int repliesNumber = TeamsRequests.getTeamsPostsReplies(teamPostId).size();

                if (repliesNumber != 0) {
                    String showRepliesText = "";

                    if (holder.dataTeamPostsRepliesList.size() == 0) {
                        holder.dataTeamPostsRepliesList.addAll(TeamsRequests.getTeamsPostsReplies(teamPostId));
                        showRepliesText = "Hide replies";
                    } else {
                        showRepliesText = "Show " + repliesNumber + " replies";
                        holder.dataTeamPostsRepliesList.clear();
                    }

                    holder.adapterTeamsPostsReplies.notifyDataSetChanged();
                    holder.card_show_replies.setText(showRepliesText);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamPostList.size();
    }

    ////////////////

    public void notifyReplyDataChanged(String changedPostId) {
        for (AdapterTeamPost.MyViewHolder holder : holderList)
            if (holder.teamPostId.equals(changedPostId)) {
                holder.dataTeamPostsRepliesList.clear();
                holder.dataTeamPostsRepliesList.addAll(TeamsRequests.getTeamsPostsReplies(holder.teamPostId));
                holder.adapterTeamsPostsReplies.notifyDataSetChanged();

                int repliesNumber = TeamsRequests.getTeamsPostsReplies(changedPostId).size();
                String showReplies = "";
                if (repliesNumber == 0)
                    showReplies = "There are no replies";
                else
                    showReplies = "Hide replies";
                holder.card_show_replies.setText(showReplies);

                break;
            }
    }
}