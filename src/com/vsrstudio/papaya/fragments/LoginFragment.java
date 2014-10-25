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
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.vsrstudio.papaya.Papaya;
import com.vsrstudio.papaya.R;
import com.vsrstudio.papaya.activities.LoginActivity;
import com.vsrstudio.papaya.model.User;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private TextView email;
    private TextView password;

    public LoginFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        context = rootView.getContext();

        email = (TextView) rootView.findViewById(R.id.email);
        email.setTypeface(Papaya.robotoLight);

        password = (TextView) rootView.findViewById(R.id.password);
        password.setTypeface(Papaya.robotoLight);

        final Button loginButton = (Button) rootView.findViewById(R.id.login_button);
        loginButton.setTypeface(Papaya.robotoLight);
        loginButton.setOnClickListener(this);

        final Button dontHaveAccountButton = (Button) rootView.findViewById(R.id.no_account_button);
        dontHaveAccountButton.setTypeface(Papaya.robotoLight);
        dontHaveAccountButton.setOnClickListener(this);

        return rootView;
    }

    private void loginUser() {
        final String email = String.valueOf(this.email.getText());
        final String password = String.valueOf(this.password.getText());

        User.logInUser(email, password, new LogInCallback() {
            public void done(ParseUser parseUser, ParseException e) {
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
            case R.id.login_button:
                loginUser();
                break;
            case R.id.no_account_button:
                ((LoginActivity) getActivity()).showRegistration();
                break;
        }
    }
}
