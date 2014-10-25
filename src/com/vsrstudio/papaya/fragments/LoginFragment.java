package com.vsrstudio.papaya.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vsrstudio.papaya.R;

public class LoginFragment extends Fragment {

    private Context context;
    private TextView username;
    private TextView password;

    public LoginFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        context = rootView.getContext();



        return rootView;
    }

}
