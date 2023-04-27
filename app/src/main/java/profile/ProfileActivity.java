package profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import com.example.register.R;

import authentication.FragmentLogIn;
import interfaces.ActivityBasics;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Bundle b = getIntent().getExtras() ;
        //String username = b.getString("username") ;

        TextView usernameEdit = findViewById(R.id.edit_username) ;
        //usernameEdit.setText(username);

    }
}
