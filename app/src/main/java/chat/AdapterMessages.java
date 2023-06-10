package chat;

import static database_connection.MessagesRequests.getUsername;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.R;

import java.util.ArrayList;

import others.PreferencesManager;

public class AdapterMessages extends RecyclerView.Adapter<AdapterMessages.MyViewHolder> {

    private final ArrayList<DataMessageCard> messagesList;
    private Context context;

    private OnMessageCardOpenButtonClickListener onMessageCardOpenButtonClickListener;

    public void setOnMessageCardOpenButtonClickListener(OnMessageCardOpenButtonClickListener onMessageCardOpenButtonClickListener) {
        this.onMessageCardOpenButtonClickListener = onMessageCardOpenButtonClickListener;
    }


    public AdapterMessages(ArrayList<DataMessageCard> messagesList, Context context) {
        this.messagesList = messagesList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //private final TextView card_sender_id;
        //private final TextView card_receiver_id;
        private final TextView card_date_posted;
        private final TextView card_text;
        private final TextView card_sender_username;
        private final TextView card_receiver_username;


        public MyViewHolder(final View view) {
            super(view);
            card_sender_username = view.findViewById(R.id.card_message_sender_username_TW);
            card_receiver_username = view.findViewById(R.id.card_message_receiver_username_TW);
            //card_sender_id = view.findViewById(R.id.card_message_sender_id_TW);
            //card_receiver_id = view.findViewById(R.id.card_message_receiver_id_TW);
            card_date_posted = view.findViewById(R.id.card_message_date_posted_TW);
            card_text = view.findViewById(R.id.card_message_text_TW);
        }
    }

    @NonNull
    @Override
    public AdapterMessages.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View message_card_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_message, parent, false);
        return new MyViewHolder(message_card_view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String currentUserId = PreferencesManager.getUserId(context);
        String username = getUsername(currentUserId);

        String senderUsername = messagesList.get(position).getSenderUsername();
        holder.card_sender_username.setText(senderUsername);

        String receiverUsername = messagesList.get(position).getReceiverUsername();
        holder.card_receiver_username.setText(receiverUsername);

        String date_posted = messagesList.get(position).getDatePosted();
        holder.card_date_posted.setText(date_posted);

        String text = messagesList.get(position).getText();
        holder.card_text.setText(text);

//        String id = messagesList.get(position).getId();
    }



    @Override
    public int getItemCount() {
        return messagesList.size();
    }


}