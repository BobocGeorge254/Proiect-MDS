package authentication;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.register.R;

import database_connection.AuthenticationRequests;
import interfaces.ActivityBasics;
import others.Manager;

public class FragmentRegister extends Fragment implements ActivityBasics {

    private EditText act_authentication_fr_register_email_ET;
    private EditText act_authentication_fr_register_username_ET;
    private EditText act_authentication_fr_register_password_ET;
    private Button act_authentication_fr_register_backToLogIn_button;
    private Button act_authentication_fr_register_register_button;
    private TextView act_authentication_fr_register_warning_TW;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_authentication_register, container, false);

        getActivityElements();
        setListeners();

        act_authentication_fr_register_warning_TW.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void getActivityElements() {
        act_authentication_fr_register_email_ET= view.findViewById(R.id.act_authentication_fr_register_email_ET);
        act_authentication_fr_register_username_ET = view.findViewById(R.id.act_authentication_fr_register_username_ET);
        act_authentication_fr_register_password_ET = view.findViewById(R.id.act_authentication_fr_register_password_ET);
        act_authentication_fr_register_backToLogIn_button = view.findViewById(R.id.act_authentication_fr_register_backToLogIn_button);
        act_authentication_fr_register_register_button = view.findViewById(R.id.act_authentication_fr_register_register_button);
        act_authentication_fr_register_warning_TW = view.findViewById(R.id.act_authentication_fr_register_warning_TW);
    }

    @Override
    public void setListeners() {
        act_authentication_fr_register_register_button_onClick();
        act_authentication_fr_register_backToLogIn_button_onClick();
    }

    private void act_authentication_fr_register_register_button_onClick()
    {
        act_authentication_fr_register_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = act_authentication_fr_register_email_ET.getText().toString().trim();
                String username = act_authentication_fr_register_username_ET.getText().toString().trim();
                String password = act_authentication_fr_register_password_ET.getText().toString().trim();

                String message = AuthenticationRequests.registerUser(email, username, password);
                act_authentication_fr_register_warning_TW.setVisibility(View.VISIBLE);
                act_authentication_fr_register_warning_TW.setText(message);

                if(message.equals("User added successfully")) {
                    act_authentication_fr_register_warning_TW.setTextColor(Color.GREEN);
                }
                else
                    act_authentication_fr_register_warning_TW.setTextColor(Color.RED);
            }
        });
    }

    private void act_authentication_fr_register_backToLogIn_button_onClick()
    {
        act_authentication_fr_register_backToLogIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_authentication_fr_register_warning_TW.setVisibility(View.INVISIBLE);
                setLogInFragment();
            }
        });
    }

    private void setLogInFragment()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_authentication_frameLayout, new FragmentLogIn());
        fragmentTransaction.commit();
    }
}