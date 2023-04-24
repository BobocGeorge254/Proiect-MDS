package teams;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.register.R;

import java.util.ArrayList;

import database_connection.FileRequest;
import database_connection.TeamsRequests;
import interfaces.ActivityBasics;
import others.PreferencesManager;

public class FragmentTeamsFilesList extends Fragment implements ActivityBasics {

    private Button act_teams_fr_team_files_listing_upload_button;
    private RecyclerView act_teams_fr_team_files_listing_recycleview;
    private ActivityResultLauncher<String> uploadFileExplorer;
    private View view;
    private AdapterTeamFile adapterFiles;
    private ArrayList<DataTeamFile> dataTeamFilesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_teams_files_list, container, false);

        getActivityElements();
        setListeners();

        dataTeamFilesList = TeamsRequests.getTeamsFilesData(PreferencesManager.getLastOpenedTeamId(getContext()));
        setTeamsAdapter();

        setUploadFileExplorer();
        return view;
    }

    @Override
    public void getActivityElements() {
        act_teams_fr_team_files_listing_upload_button = view.findViewById(R.id.act_teams_fr_team_files_listing_upload_button);
        act_teams_fr_team_files_listing_recycleview = view.findViewById(R.id.act_teams_fr_team_files_listing_recycleview);
    }

    @Override
    public void setListeners() {
        act_teams_fr_team_files_listing_upload_button_onClick();
    }

    private void act_teams_fr_team_files_listing_upload_button_onClick() {
        act_teams_fr_team_files_listing_upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFileExplorer.launch("*/*");
            }
        });
    }

    private void setUploadFileExplorer() {
        uploadFileExplorer = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                String fileName = null;
                String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};
                try (Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME));
                    }
                }

                if(fileName == null)
                    fileName = "Unknown file";

                String response = FileRequest.uploadTeamsFile(getContext(), uri);

                if(!response.equals("Failed to upload team file")) {
                    TeamsRequests.addTeamFile(fileName, response, PreferencesManager.getLastOpenedTeamId(getContext()));
                    dataTeamFilesList.clear();
                    dataTeamFilesList.addAll(TeamsRequests.getTeamsFilesData(PreferencesManager.getLastOpenedTeamId(getContext())));
                    adapterFiles.notifyDataSetChanged();
                }
            }
        });
    }

    private void setTeamsAdapter() {
        adapterFiles = new AdapterTeamFile(dataTeamFilesList, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        act_teams_fr_team_files_listing_recycleview.setLayoutManager(layoutManager);
        act_teams_fr_team_files_listing_recycleview.setItemAnimator(new DefaultItemAnimator());
        act_teams_fr_team_files_listing_recycleview.setAdapter(adapterFiles);

        adapterFiles.setOnTeamFileDeleteButtonClickListener(new OnTeamFileDeleteButtonClickListener() {
            @Override
            public void onCardItemClick(String fileId, String uri) {
                String response = TeamsRequests.deleteTeamFile(fileId);

                if(response.equals("File deleted successfully")) {
                    FileRequest.deleteFile(uri);
                    dataTeamFilesList.clear();
                    dataTeamFilesList.addAll(TeamsRequests.getTeamsFilesData(PreferencesManager.getLastOpenedTeamId(getContext())));
                    adapterFiles.notifyDataSetChanged();
                }
            }
        });
    }
}