package chat;

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
        private final TextView card_sender_id;
        private final TextView card_receiver_id;
        private final TextView card_date_posted;
        private final TextView card_text;
        private final Button card_open_button;

        public MyViewHolder(final View view) {
            super(view);
            card_sender_id = view.findViewById(R.id.card_message_sender_id_TW);
            card_receiver_id = view.findViewById(R.id.card_message_receiver_id_TW);
            card_date_posted = view.findViewById(R.id.card_message_date_posted_TW);
            card_text = view.findViewById(R.id.card_message_text_TW);
            card_open_button = view.findViewById(R.id.card_message_open_button);
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
        String sender_id = messagesList.get(position).getSenderId();
        holder.card_sender_id.setText(sender_id);

        String receiver_id = messagesList.get(position).getReceiverId();
        holder.card_receiver_id.setText(receiver_id);

        String date_posted = messagesList.get(position).getDatePosted();
        holder.card_date_posted.setText(date_posted);

        String text = messagesList.get(position).getText();
        holder.card_text.setText(text);

        String id = messagesList.get(position).getId();

        holder.card_open_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMessageCardOpenButtonClickListener != null)
                    onMessageCardOpenButtonClickListener.onCardItemClick(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }


}