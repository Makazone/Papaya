package com.vsrstudio.papaya.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.vsrstudio.papaya.Papaya;
import com.vsrstudio.papaya.R;
import com.vsrstudio.papaya.activities.LoginActivity;

public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private TextView username;
    private TextView password;

    public RegistrationFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_registration, container, false);
        context = rootView.getContext();

        username = (TextView) rootView.findViewById(R.id.username);
        username.setTypeface(Papaya.robotoLight);

        password = (TextView) rootView.findViewById(R.id.password);
        password.setTypeface(Papaya.robotoLight);

        final Button registerButton = (Button) rootView.findViewById(R.id.register_button);
        registerButton.setTypeface(Papaya.robotoLight);
        registerButton.setOnClickListener(this);

        final Button alreadyHaveAccountButton = (Button) rootView.findViewById(R.id.already_button);
        alreadyHaveAccountButton.setTypeface(Papaya.robotoLight);
        alreadyHaveAccountButton.setOnClickListener(this);

        return rootView;
    }

    private void registerUser() {
        final String username = String.valueOf(this.username.getText());
        final String password = String.valueOf(this.password.getText());

        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    ((LoginActivity) getActivity()).startMainActivity();
                } else {
                    e.printStackTrace();
                    Toast.makeText(context, R.string.connection_error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_button:
                registerUser();
                break;
            case R.id.already_button:
                ((LoginActivity) getActivity()).showLogin();
                break;
        }
    }
}