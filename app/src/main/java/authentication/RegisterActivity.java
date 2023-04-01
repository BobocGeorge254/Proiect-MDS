package authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.register.R;

import database_connection.AuthenticationRequests;
import interfaces.ActivityBasics;
import others.Manager;

public class RegisterActivity extends AppCompatActivity implements ActivityBasics {

    private EditText act_register_email_ET;
    private EditText act_register_username_ET;
    private EditText act_register_password_ET;
    private Button act_register_backToLogIn_button;
    private Button act_register_register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getActivityElements();
        setListeners();
    }

    @Override
    public void getActivityElements() {
        act_register_email_ET = findViewById(R.id.act_register_email_ET);
        act_register_username_ET = findViewById(R.id.act_register_username_ET);
        act_register_password_ET= findViewById(R.id.act_register_password_ET);
        act_register_backToLogIn_button = findViewById(R.id.act_register_backToLogIn_button);
        act_register_register_button = findViewById(R.id.act_register_register_button);
    }

    @Override
    public void setListeners() {
        act_register_backToLogIn_button_onClick();
        act_register_register_button_onClick();
    }

    private void act_register_backToLogIn_button_onClick()
    {
        act_register_backToLogIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void act_register_register_button_onClick()
    {
        act_register_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = act_register_email_ET.getText().toString().trim();
                String username = act_register_username_ET.getText().toString().trim();
                String password = act_register_password_ET.getText().toString().trim();

                System.out.println(Manager.authenticationRequests.registerUser(email, username, password));
            }
        });
    }
}