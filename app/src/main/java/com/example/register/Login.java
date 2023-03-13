package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    DatabaseHelper myDB ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        myDB = new DatabaseHelper(this) ;
    }
    public void register(View view) {
        Intent switchActivity = new Intent(this, MainActivity.class);
        startActivity(switchActivity);
    }
    public void btnLogin(View view) {
        EditText emailEdit = findViewById(R.id.editTextTextPersonName4) ;
        EditText passwordEdit = findViewById(R.id.editTextTextPersonName5) ;
        Cursor res = myDB.getAllData() ;
        while(res.moveToNext()) {
            if (res.getString(3).toString().equals(emailEdit.getText().toString())) {
                if (res.getString(4).toString().equals(passwordEdit.getText().toString())) {
                    Intent switchActivity = new Intent(this, Hello.class);
                    startActivity(switchActivity);
                }
            }
        }
    }
}