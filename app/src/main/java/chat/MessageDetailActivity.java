package chat;

import static database_connection.MessagesRequests.getUsername;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.register.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import database_connection.MessagesRequests;
import interfaces.ActivityBasics;
import others.Manager;

public class MessageDetailActivity extends AppCompatActivity implements ActivityBasics {

    private RecyclerView recyclerView;
    private AdapterMessages adapterMessages;
    private ArrayList<DataMessageCard> messagesColletion = new ArrayList<>();
    private String currentUserId;
    private String friendId;

    private Button act_message_back_button;
    private Button act_chat_seend_message_button;
    private EditText textbox_seend_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        getActivityElements();

        // Obțineți ID-urile utilizatorilor din intent
        Intent intent = getIntent();
        currentUserId = intent.getStringExtra("currentUserId");
        friendId = intent.getStringExtra("friendId");

        // Obțineți mesajele relevante
        messagesColletion = MessagesRequests.GetUserMessages(currentUserId, friendId);
        messagesColletion = sortMessagesByDate(messagesColletion);

        // Setarea recycler view și a adapterului pentru mesaje
        recyclerView = findViewById(R.id.recycler_view_messages);
        adapterMessages = new AdapterMessages(messagesColletion, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterMessages);

        //int verticalSpacingInPixels = getResources().getDimensionPixelSize(R.dimen.vertical_spacing); // Specificați dimensiunea dorită a decalajului în dimensiunea pixelilor
        //recyclerView.addItemDecoration(new SpacingItemDecoration(verticalSpacingInPixels));

        setListeners();

        act_message_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void getActivityElements() {
        act_chat_seend_message_button = findViewById(R.id.act_chat_seend_message_button);
        textbox_seend_message = findViewById(R.id.textbox_seend_message);
        act_message_back_button = findViewById(R.id.act_message_back_button);
    }

    @Override
    public void setListeners() {
        act_chat_seend_message_button_onClick();
    }

    private void act_chat_seend_message_button_onClick() {
        act_chat_seend_message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textbox_content = textbox_seend_message.getText().toString();

                // Obține data curentă
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();

                // Formatează data curentă cu ajutorul obiectului dateFormat
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(currentDate);

                //DataMessageCard message = new DataMessageCard(currentUserId, friendId,formattedDate, textbox_content);
                String currentUsername = getUsername(currentUserId);
                String friendUsername = getUsername(friendId);

                CollectionReference messagesCollection = Manager.dbConnection.getDatabase().collection("Messages");
                Map<String, Object> message = new HashMap<>();
                message.put("senderUsername", currentUsername);
                message.put("receiverUsername", friendUsername);
                message.put("sender_id", currentUserId);
                message.put("receiver_id", friendId);
                message.put("datePosted", formattedDate);
                message.put("text", textbox_content);

                Task<DocumentReference> addMessageTask = messagesCollection.add(message);
                addMessageTask.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Mesajul a fost adăugat cu succes
                        textbox_seend_message.setText(""); // Golește conținutul câmpului de text

                        // Actualizează lista de mesaje
                        messagesColletion.add(new DataMessageCard(currentUsername, friendUsername, currentUserId, friendId, formattedDate, textbox_content));
                        adapterMessages.notifyDataSetChanged();

                        // Derulează recycler view la ultimul mesaj adăugat
                        recyclerView.scrollToPosition(messagesColletion.size() - 1);
                    } else {
                        // Mesajul nu a putut fi adăugat
                        // Tratați eroarea în consecință
                    }
                });


//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference messagesRef = database.getReference("Messages");
//                messagesRef.push().setValue(message);
            }
        });
    }



    public static ArrayList<DataMessageCard> sortMessagesByDate(ArrayList<DataMessageCard> messages) {
        Collections.sort(messages, new Comparator<DataMessageCard>() {
            @Override
            public int compare(DataMessageCard m1, DataMessageCard m2) {
                String date1 = m1.getDatePosted();
                String date2 = m2.getDatePosted();

                if (date1 == null && date2 == null) {
                    return 0; // Ambele obiecte sunt nule, deci sunt considerate egale
                } else if (date1 == null) {
                    return -1; // Primul obiect este nul, deci este considerat mai mic
                } else if (date2 == null) {
                    return 1; // Al doilea obiect este nul, deci este considerat mai mic
                } else {
                    return date1.compareTo(date2); // Compararea normală
                }
            }
        });
        return messages;
    }
}