package teams;

import android.content.Context;
import android.net.Uri;
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

import database_connection.FileRequest;

public class AdapterTeamFile extends RecyclerView.Adapter<AdapterTeamFile.MyViewHolder> {

    private final ArrayList<DataTeamFile> teamFileList;
    private Context context;
    private OnTeamFileDeleteButtonClickListener onTeamFileDeleteButtonClickListener;

    public void setOnTeamFileDeleteButtonClickListener(OnTeamFileDeleteButtonClickListener onTeamFileDeleteButtonClickListener) {
        this.onTeamFileDeleteButtonClickListener = onTeamFileDeleteButtonClickListener;
    }

    public AdapterTeamFile(ArrayList<DataTeamFile> teamFileList, Context context) {
        this.teamFileList = teamFileList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView card_name;
        private final ImageButton card_delete;

        public MyViewHolder(final View view) {
            super(view);
            card_name = view.findViewById(R.id.card_team_file_name);
            card_delete = view.findViewById(R.id.card_team_file_delete);
        }
    }

    @NonNull
    @Override
    public AdapterTeamFile.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View team_file_name_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_team_file, parent, false);
        return new AdapterTeamFile.MyViewHolder(team_file_name_view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTeamFile.MyViewHolder holder, int position) {
        String name = teamFileList.get(position).getName();
        holder.card_name.setText(name);

        String filUri = teamFileList.get(position).getUri();
        String fileId = teamFileList.get(position).getId();

        holder.card_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileRequest.downloadFile(context, Uri.parse(filUri));
            }
        });

        holder.card_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTeamFileDeleteButtonClickListener.onCardItemClick(fileId, filUri);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamFileList.size();
    }
}
