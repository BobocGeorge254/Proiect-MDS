package activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.register.R;

import interfaces.ActivityBasics;

public class TeamsListingActivity extends AppCompatActivity implements ActivityBasics {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams_listing);
    }

    @Override
    public void getActivityElements() {

    }

    @Override
    public void setListeners() {

    }
}