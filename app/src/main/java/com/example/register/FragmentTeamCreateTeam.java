package com.example.register;

import android.graphics.Bitmap;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import database_connection.FileRequest;
import database_connection.TeamsRequests;
import interfaces.ActivityBasics;
import others.PreferencesManager;
import others.Utils;
import teams.DataTeamCard;
import teams.FragmentTeamsListing;

public class FragmentTeamCreateTeam extends Fragment implements ActivityBasics {

    private EditText act_teams_fr_listing_create_team_card_name;
    private EditText act_teams_fr_listing_create_team_card_description;
    private FrameLayout act_teams_fr_listing_create_team_card_create_button;
    private FrameLayout act_teams_fr_listing_create_team_card_back_button;
    private LinearLayout act_teams_fr_listing_create_team_card_upload_image_button;
    private ImageView act_teams_fr_listing_create_team_card_team_imageView;
    private View view;
    private ActivityResultLauncher<String> createTeamUploadImageFileExplorer;
    private boolean selectedImage = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_teams_create_team, container, false);

        getActivityElements();
        setListeners();

        setCreateTeamUploadImageFileExplorer();

        return view;
    }

    @Override
    public void getActivityElements() {
        act_teams_fr_listing_create_team_card_name = view.findViewById(R.id.act_teams_fr_listing_create_team_card_name);
        act_teams_fr_listing_create_team_card_description = view.findViewById(R.id.act_teams_fr_listing_create_team_card_description);
        act_teams_fr_listing_create_team_card_create_button = view.findViewById(R.id.act_teams_fr_listing_create_team_card_create_button);
        act_teams_fr_listing_create_team_card_back_button = view.findViewById(R.id.act_teams_fr_listing_create_team_card_back_button);
        act_teams_fr_listing_create_team_card_upload_image_button = view.findViewById(R.id.act_teams_fr_listing_create_team_card_upload_image_button);
        act_teams_fr_listing_create_team_card_team_imageView = view.findViewById(R.id.act_teams_fr_listing_create_team_card_team_imageView);
    }

    @Override
    public void setListeners() {
        act_teams_fr_listing_create_team_card_create_button_onClick();
        act_teams_fr_listing_create_team_card_back_button_onClick();
        act_teams_fr_listing_create_team_card_upload_image_button_onClick();
    }

    private void act_teams_fr_listing_create_team_card_create_button_onClick() {
        act_teams_fr_listing_create_team_card_create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uploadLogoResponse = "No image selected";
                String photoUri = "";

                if(selectedImage)
                    uploadLogoResponse = FileRequest.uploadTeamLogo(getContext(), PreferencesManager.getLastURISelected(getContext()));

                String teamName = act_teams_fr_listing_create_team_card_name.getText().toString().trim();
                String teamDescription = act_teams_fr_listing_create_team_card_description.getText().toString().trim();

                if(!(uploadLogoResponse.equals("No image selected") || uploadLogoResponse.equals("Failed to upload team image")))
                    photoUri = uploadLogoResponse;
                else
                    photoUri = "default.png";

                String response = TeamsRequests.createTeam(teamName, teamDescription, photoUri, PreferencesManager.getUserId(getContext()));
                System.out.println(response);

                if (!response.equals("Error adding team")) {
                    setTeamsListingFragment();
                }
            }
        });
    }

    private void act_teams_fr_listing_create_team_card_back_button_onClick() {
        act_teams_fr_listing_create_team_card_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTeamsListingFragment();
            }
        });
    }

    private void act_teams_fr_listing_create_team_card_upload_image_button_onClick() {
        act_teams_fr_listing_create_team_card_upload_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTeamUploadImageFileExplorer.launch("image/*");
            }
        });
    }

    private void setCreateTeamUploadImageFileExplorer() {
        createTeamUploadImageFileExplorer = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                PreferencesManager.saveLastURISelected(getContext(), uri);
                selectedImage = true;

                Bitmap bitmap = Utils.transformUriToBitmap(getContext(), uri);
                if(bitmap.getHeight() != 0)
                    act_teams_fr_listing_create_team_card_team_imageView.setImageBitmap(bitmap);
            }
        });
    }

    private void setTeamsListingFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_teams_frameLayout, new FragmentTeamsListing());
        fragmentTransaction.commit();
    }
}