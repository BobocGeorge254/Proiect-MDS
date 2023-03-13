package com.example.register;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this) ;
    }

    public void btnOnClick(View view) {
        EditText firstNameEdit = findViewById(R.id.editTextTextPersonName) ;
        EditText lastNameEdit = findViewById(R.id.editTextTextPersonName2) ;
        EditText emailEdit = findViewById(R.id.editTextTextPersonName3) ;
        EditText passwordEdit = findViewById(R.id.editTextTextPassword) ;

        TextView firstNameView = findViewById(R.id.textView) ;
        TextView lastNameView = findViewById(R.id.textView2) ;
        TextView emailView = findViewById(R.id.textView3) ;

        firstNameView.setText("First name: " +firstNameEdit.getText().toString());
        lastNameView.setText("Last name: " + lastNameEdit.getText().toString());
        emailView.setText("Email: " + emailEdit.getText().toString());
        boolean isInserted = myDB.insertData(
                firstNameEdit.getText().toString(),
                    lastNameEdit.getText().toString(),
                        emailEdit.getText().toString(),
                            passwordEdit.getText().toString()
        ) ;
        if (isInserted = true)
            Toast.makeText(MainActivity.this,"Data Inserted", Toast.LENGTH_LONG).show() ;
    }

    public void btnOnClick2(View view) {
        Cursor res = myDB.getAllData() ;
        if (res.getCount() == 0) {
            ShowMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer() ;
        while (res.moveToNext()){
            buffer.append("Id: " + res.getString(0) + "\n") ;
            buffer.append("First name: " + res.getString(1) + "\n") ;
            buffer.append("Last name: " + res.getString(2) + "\n") ;
            buffer.append("Email: " + res.getString(3) + "\n") ;
            buffer.append("Password: " + res.getString(4) + "\n") ;
        }
        ShowMessage("Data",buffer.toString());
    }

    public void ShowMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this) ;
        builder.setCancelable(true) ;
        builder.setTitle(title) ;
        builder.setMessage(message) ;
        builder.show() ;
    }

    public void login(View view) {
        Intent switchActivity = new Intent(this, Login.class);
        startActivity(switchActivity);
    }
}