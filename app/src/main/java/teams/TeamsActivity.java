package teams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.register.R;

import authentication.FragmentLogIn;
import interfaces.ActivityBasics;

public class TeamsActivity extends AppCompatActivity implements ActivityBasics {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        setTeamsListingFragment();
    }

    @Override
    public void getActivityElements() {

    }

    @Override
    public void setListeners() {

    }

    private void setTeamsListingFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_teams_frameLayout, new FragmentTeamsListing());
        fragmentTransaction.commit();
    }
}