package profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.register.R;

import authentication.FragmentLogIn;
import chat.DataUserCard;
import interfaces.ActivityBasics;
import others.Manager;
import others.PreferencesManager;

import database_connection.MessagesRequests ;
import database_connection.OtherRequests ;
import teams.TeamsActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;

public class ProfileActivity extends AppCompatActivity {

    private String updatedUsername ;
    private String updatedEmail ;
    private String updatedPassword  ;

    private String updatedPhoto ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String userId = others.PreferencesManager.getUserId(ProfileActivity.this) ;

        ImageView imageEdit = findViewById(R.id.imageView) ;
        Button imageEditButton = findViewById(R.id.button4) ;

        EditText usernameEdit = findViewById(R.id.edit_username) ;
        Button usernameEditButton = findViewById(R.id.button) ;

        EditText emailEdit = findViewById(R.id.editTextEmail) ;
        Button emailEditButton = findViewById(R.id.button2) ;

        EditText passwordEdit = findViewById(R.id.editTextTextPassword) ;
        Button passwordEditButton = findViewById(R.id.button3) ;

        DataUserCard dataUserCard = database_connection.MessagesRequests.getDataUser(userId) ;
        usernameEdit.setText(dataUserCard.getUsername());
        emailEdit.setText(dataUserCard.getEmail());
        passwordEdit.setText(dataUserCard.getPassword());

        Button backButton = findViewById(R.id.buttonBack) ;

        updatedUsername = usernameEdit.getText().toString() ;
        updatedEmail = emailEdit.getText().toString() ;
        updatedPassword = passwordEdit.getText().toString() ;

        usernameEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUsername = usernameEdit.getText().toString() ;
                if (newUsername.isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please enter a valid username!", Toast.LENGTH_SHORT).show();
                }
                else {
                    updatedUsername = newUsername ;
                    database_connection.OtherRequests.updateUser(userId, updatedUsername, updatedEmail, updatedPassword);
                    Toast.makeText(ProfileActivity.this, "Username updated succesfuly!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        emailEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEmail = emailEdit.getText().toString() ;
                if (newEmail.isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please enter a valid e-mail!", Toast.LENGTH_SHORT).show();
                }
                else {
                    updatedEmail = newEmail ;
                    database_connection.OtherRequests.updateUser(userId, updatedUsername, updatedEmail, updatedPassword);
                    Toast.makeText(ProfileActivity.this, "Email updated succesfuly!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        passwordEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = passwordEdit.getText().toString() ;
                if (newPassword.isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please enter a valid password!", Toast.LENGTH_SHORT).show();
                }
                else {
                    updatedPassword = newPassword ;
                    database_connection.OtherRequests.updateUser(userId, updatedUsername, updatedEmail, updatedPassword);
                    Toast.makeText(ProfileActivity.this, "Password updated succesfuly!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, TeamsActivity.class);
                startActivity(intent);
            }
        });
    }
}
