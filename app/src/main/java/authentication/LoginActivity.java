package authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.register.R;

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