package activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.register.R;

import database_connection.AuthenticationRequests;
import interfaces.ActivityBasics;

public class LoginActivity extends AppCompatActivity implements ActivityBasics {

    private EditText act_login_username_email_ET;
    private EditText act_login_password_ET;
    private Button act_login_signIn_button;
    private Button act_login_toRegister_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActivityElements();
        setListeners();
    }

    @Override
    public void getActivityElements() {
        act_login_username_email_ET = findViewById(R.id.act_login_username_email_ET);
        act_login_password_ET = findViewById(R.id.act_login_password_ET);
        act_login_signIn_button = findViewById(R.id.act_login_signIn_button);
        act_login_toRegister_button = findViewById(R.id.act_login_toRegister_button);
    }

    @Override
    public void setListeners() {
        act_login_toRegister_button_onClick();
        act_login_signIn_button_onClick();
    }

    private void act_login_signIn_button_onClick()
    {
        act_login_signIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_or_email = act_login_username_email_ET.getText().toString().trim();
                String password = act_login_password_ET.getText().toString().trim();

                String status = AuthenticationRequests.checkUserExists(email_or_email, email_or_email, password);
                System.out.println(status);

                if(status.equals("User login successfully"))
                    startActivity(new Intent(LoginActivity.this, TeamsListingActivity.class));
            }
        });
    }

    private void act_login_toRegister_button_onClick()
    {
        act_login_toRegister_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}