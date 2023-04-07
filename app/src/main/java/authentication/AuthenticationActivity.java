package authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.register.R;

import interfaces.ActivityBasics;

public class AuthenticationActivity extends AppCompatActivity implements ActivityBasics {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        setLogInFragment();
    }

    @Override
    public void getActivityElements() {

    }

    @Override
    public void setListeners() {

    }

    private void setLogInFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_authentication_frameLayout, new FragmentLogIn());
        fragmentTransaction.commit();
    }
}