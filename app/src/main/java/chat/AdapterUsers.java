package chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.R;

import java.security.AccessControlContext;
import java.util.ArrayList;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyViewHolder>{

    private final String currentUserId;
    private final ArrayList<DataUserCard> usersList;
    private Context context;

    private OnUserCardOpenButtonClickListener onUserCardOpenButtonClickListener;

    public void setOnUserCardOpenButtonClickListener(OnUserCardOpenButtonClickListener onUserCardOpenButtonClickListener) {
        this.onUserCardOpenButtonClickListener = onUserCardOpenButtonClickListener;
    }

    public void onCardUserClick(String userId) {
        System.out.println("MERGEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
    }

    public AdapterUsers(String currentUserId, ArrayList<DataUserCard> usersList, Context context) {
        this.currentUserId = currentUserId;
        this.usersList = usersList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView card_email;
        private final TextView card_username;
        private final Button card_open_button;

        public MyViewHolder(final View view) {
            super(view);
            card_email = view.findViewById(R.id.card_user_email_TW);
            card_username = view.findViewById(R.id.card_user_username_TW);
            card_open_button = view.findViewById(R.id.card_open_button);
        }
    }

    @NonNull
    @Override
    public AdapterUsers.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View user_card_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user, parent, false);
        return new MyViewHolder(user_card_view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String currentUserId = this.currentUserId;
        String email = usersList.get(position).getEmail();
        holder.card_email.setText(email);

        String username = usersList.get(position).getUsername();
        holder.card_username.setText(username);

        String id = usersList.get(position).getId();

        holder.card_open_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onUserCardOpenButtonClickListener != null)
                    onUserCardOpenButtonClickListener.onCardUserClick(currentUserId, id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

}


