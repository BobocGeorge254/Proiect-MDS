package authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.register.R;

import database_connection.AuthenticationRequests;
import interfaces.ActivityBasics;

public class FragmentLogIn extends Fragment implements ActivityBasics {

    private EditText act_authentication_fr_authentication_username_email_ET;
    private EditText act_authentication_fr_authentication_password_ET;
    private Button act_authentication_fr_authentication_signIn_button;
    private Button act_authentication_fr_authentication_toRegister_button;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_authentication_login, container, false);

        getActivityElements();
        setListeners();

        return view;
    }

    @Override
    public void getActivityElements() {
        act_authentication_fr_authentication_username_email_ET = view.findViewById(R.id.act_authentication_fr_login_username_email_ET);
        act_authentication_fr_authentication_password_ET = view.findViewById(R.id.act_authentication_fr_login_password_ET);
        act_authentication_fr_authentication_signIn_button = view.findViewById(R.id.act_authentication_fr_login_signIn_button);
        act_authentication_fr_authentication_toRegister_button = view.findViewById(R.id.act_authentication_fr_login_toRegister_button);
    }

    @Override
    public void setListeners() {
        act_authentication_fr_authentication_signIn_button_onClick();
        act_authentication_fr_authentication_toRegister_button_onClick();
    }

    private void act_authentication_fr_authentication_signIn_button_onClick()
    {
        act_authentication_fr_authentication_signIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_or_email = act_authentication_fr_authentication_username_email_ET.getText().toString().trim();
                String password = act_authentication_fr_authentication_password_ET.getText().toString().trim();

                String status = AuthenticationRequests.checkUserExists(email_or_email, email_or_email, password);
                System.out.println(status);
            }
        });
    }

    private void act_authentication_fr_authentication_toRegister_button_onClick()
    {
        act_authentication_fr_authentication_toRegister_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRegisterFragment();
            }
        });
    }

    private void setRegisterFragment()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.act_authentication_frameLayout, new FragmentRegister());
        fragmentTransaction.commit();
    }
}