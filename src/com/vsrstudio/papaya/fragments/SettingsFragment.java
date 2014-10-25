package com.vsrstudio.papaya.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.vsrstudio.papaya.Papaya;
import com.vsrstudio.papaya.R;
import com.vsrstudio.papaya.activities.LoginActivity;
import com.vsrstudio.papaya.model.User;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private Context context;

    public SettingsFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        context = rootView.getContext();

        final Button logoutButton = (Button) rootView.findViewById(R.id.logout_button);
        logoutButton.setTypeface(Papaya.robotoLight);
        logoutButton.setOnClickListener(this);

        final Button deleteUserButton = (Button) rootView.findViewById(R.id.delete_user_button);
        deleteUserButton.setTypeface(Papaya.robotoLight);
        deleteUserButton.setOnClickListener(this);

        return rootView;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout_button:
                logoutAndExit();
                break;
            case R.id.delete_user_button:
                User.currentUser.getParseUser().deleteInBackground(new DeleteCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            logoutAndExit();
                        } else {
                            e.printStackTrace();
                            Toast.makeText(context, R.string.connection_error, Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
        }
    }

    private void logoutAndExit() {
        Intent intent;
        User.currentUser.logOut();
//        User.currentUser.getParseUser().logOut();
        intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}