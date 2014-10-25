package com.vsrstudio.papaya.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.vsrstudio.papaya.Papaya;
import com.vsrstudio.papaya.R;

public class LoginActivity extends FragmentActivity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);
        Papaya.initializeFonts(this);
        setUpActionBar();

        Papaya.setUpParse(this);

    }

//    private void showNoInternet() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, new NoInternetFragment()).commit();
//    }
//
//    private void showLoading() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, new LoadingFragment()).commit();
//    }
//
//    private void showLogin() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment()).commit();
//    }

    private void setUpActionBar() {
        // Inflate your custom layout
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar, null);

        // Set up your ActionBar
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);

        final TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(Papaya.robotoSlabRegular);
        title.setText(R.string.login);

        final ImageButton showMenuButton = (ImageButton) findViewById(R.id.show_menu);
        showMenuButton.setVisibility(View.GONE);
    }

    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

//    public static class NoInternetFragment extends android.support.v4.app.Fragment {
//        public NoInternetFragment() {
//            super();
//        }
//
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            final View rootView = inflater.inflate(R.layout.fragment_no_internet, container, false);
//
//            ((TextView) rootView.findViewById(R.id.check_your_connection)).setTypeface(Papaya.robotoLight);
//
//            final TextView tryAgainButton = (TextView) rootView.findViewById(R.id.try_again);
//            tryAgainButton.setTypeface(Papaya.robotoLight);
//            tryAgainButton.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    if (!VKSdk.isLoggedIn()) {
//                        ((LoginActivity) getActivity()).showLogin();
//                    } else if (VKSdk.wakeUpSession()) {
//                        ((LoginActivity) getActivity()).showLoading();
//                        ((LoginActivity) getActivity()).getVkId();
//                    }
//                }
//            });
//
//            return rootView;
//        }
//    }
}
