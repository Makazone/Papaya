package com.vsrstudio.papaya.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vsrstudio.papaya.R;

public class SettingsFragment extends Fragment {

    private Context context;

    public SettingsFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        context = rootView.getContext();
        return rootView;
    }

}