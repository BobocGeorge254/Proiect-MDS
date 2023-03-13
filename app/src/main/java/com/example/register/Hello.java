package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Hello extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        Bundle b = getIntent().getExtras();
        String firstName = b.getString("firstName") ;
        String lastName = b.getString("lastName") ;
        String email = b.getString("email") ;

        TextView firstNameEdit = findViewById(R.id.textView7);
        firstNameEdit.setText("FIRST NAME: " + "\n" + firstName);

        TextView lastNameEdit = findViewById(R.id.textView8);
        lastNameEdit.setText("LAST NAME: " + "\n" + lastName);

        TextView emailEdit = findViewById(R.id.textView9);
        emailEdit.setText("EMAIL: " + "\n" + email);
    }
}