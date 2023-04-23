package teams;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.register.R;

import database_connection.FileRequest;
import database_connection.TeamsRequests;
import interfaces.ActivityBasics;
import others.PreferencesManager;

public class FragmentTeamsEditTeam extends Fragment implements ActivityBasics {

    private View view;
    private EditText act_teams_fr_edit_team_name_ET;
    private EditText act_teams_fr_edit_team_description_ET;
    private Button act_teams_fr_edit_team_upload_image_button;
    private Button act_teams_fr_edit_team_update_button;
    private Button act_teams_fr_edit_team_back_button;
    private DataTeamCard initialData;

    private ActivityResultLauncher<String> updateTeamUploadImageFileExplorer;
    private boolean selectedNewImage = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_teams_edit_team, container, false);

        getActivityElements();
        setListeners();

        initialSetUp();
        setUpdateTeamUploadImageFileExplorer();

        return view;
    }

    @Override
    public void getActivityElements() {
        act_teams_fr_edit_team_name_ET = view.findViewById(R.id.act_teams_fr_edit_team_name_ET);
        act_teams_fr_edit_team_description_ET = view.findViewById(R.id.act_teams_fr_edit_team_description_ET);
        act_teams_fr_edit_team_upload_image_button = view.findViewById(R.id.act_teams_fr_edit_team_upload_image_button);
        act_teams_fr_edit_team_update_button = view.findViewById(R.id.act_teams_fr_edit_team_update_button);
        act_teams_fr_edit_team_back_button = view.findViewById(R.id.act_teams_fr_edit_team_back_button);
    }

    @Override
    public void setListeners() {
        act_teams_fr_edit_team_update_button_onClick();
        act_teams_fr_edit_team_back_button_onClick();
        act_teams_fr_edit_team_upload_image_button_onClick();
    }

    private void act_teams_fr_edit_team_update_button_onClick() {
        act_teams_fr_edit_team_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uploadLogoResponse = "No image selected";
                String updatedPhotoUri = "";

                if(selectedNewImage)
                    uploadLogoResponse = FileRequest.uploadTeamLogo(getContext(), PreferencesManager.getLastURISelected(getContext()));

                String updatedTeamName = act_teams_fr_edit_team_name_ET.getText().toString().trim();
                String updatedTeamDescription = act_teams_fr_edit_team_description_ET.getText().toString().trim();

                if(!uploadLogoResponse.equals("No image selected")) {
                    updatedPhotoUri = uploadLogoResponse;
                    FileRequest.deleteFile(initialData.getPhotoUri());
                }
                else
                    updatedPhotoUri = initialData.getPhotoUri();   //if new image upload fails it remains the same

                String response = TeamsRequests.updateTeam(PreferencesManager.getLastTeamPressedEditId(getContext()), updatedTeamName, updatedTeamDescription, updatedPhotoUri);
                System.out.println(response);

                setTeamsListingFragment();
            }
        });
    }

    private void act_teams_fr_edit_team_back_button_onClick() {
        act_teams_fr_edit_team_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTeamsListingFragment();
            }
        });
    }

    private void act_teams_fr_edit_team_upload_image_button_onClick() {
        act_teams_fr_edit_team_upload_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTeamUploadImageFileExplorer.launch("image/*");
            }
        });
    }

    private void setUpdateTeamUploadImageFileExplorer() {
        updateTeamUploadImageFileExplorer = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                PreferencesManager.saveLastURISelected(getContext(), uri);
                selectedNewImage = true;
            }
        });
    }

    private void initialSetUp() {
        initialData = TeamsRequests.getTeamData(PreferencesManager.getLastTeamPressedEditId(getContext()));
        act_teams_fr_edit_team_name_ET.setText(initialData.getName());
        act_teams_fr_edit_team_description_ET.setText(initialData.getDescription());
    }

    private void setTeamsListingFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_teams_frameLayout, new FragmentTeamsListing());
        fragmentTransaction.commit();
    }
}