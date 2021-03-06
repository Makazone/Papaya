package com.vsrstudio.papaya.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.vsrstudio.papaya.Papaya;
import com.vsrstudio.papaya.R;
import com.vsrstudio.papaya.fragments.LoadingFragment;
import com.vsrstudio.papaya.fragments.LoginFragment;
import com.vsrstudio.papaya.fragments.RegistrationFragment;
import com.vsrstudio.papaya.model.Book;
import com.vsrstudio.papaya.model.GoogleCallback;
import com.vsrstudio.papaya.model.User;

import java.util.ArrayList;

public class LoginActivity extends Activity {

    private final Context context = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);

        Papaya.initializeFonts(this);
        setUpActionBar();

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        final String email = sharedPreferences.getString("email", null);
        final String password = sharedPreferences.getString("password", null);

        if (email != null && !email.equals("")) {
            User.logInUser(email, password, new LogInCallback() {
                public void done(ParseUser parseUser, ParseException e) {
                    if (e == null) {
                        startMainActivity();
                    } else {
                        e.printStackTrace();

                        if (Papaya.isOnline(context)) {
                            Papaya.setUpParse(context);
                            if (User.currentUser == null) {
                                showRegistration();
                            } else {
                                startMainActivity();
                            }
                        } else {
                            showNoInternet();
                        }
                    }
                }
            });
        } else {
            if (Papaya.isOnline(this)) {
                Papaya.setUpParse(this);
                if (User.currentUser == null) {
                    showRegistration();
                } else {
                    startMainActivity();
                }
            } else {
                showNoInternet();
            }
        }

        try {
            Book.findBooksByString("java", new GoogleCallback<Book>() {
                @Override
                public void completedGoogleTask(ArrayList<Book> books) {
                    System.out.println("SIZEEEE" + books.size());
                    for (Book b : books) {
                        System.out.println("TITLE ALBINA" + b.getTitle());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void showRegistration() {
        ((TextView) findViewById(R.id.title)).setText(R.string.registration);
        getFragmentManager().beginTransaction().replace(R.id.container, new RegistrationFragment()).commit();
    }

    private void showNoInternet() {
        getFragmentManager().beginTransaction().replace(R.id.container, new NoInternetFragment()).commit();
    }

    public void showLoading() {
        getFragmentManager().beginTransaction().replace(R.id.container, new LoadingFragment()).commit();
    }

    public void showLogin() {
        ((TextView) findViewById(R.id.title)).setText(R.string.login);
        getFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment()).commit();
    }

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

    public static class NoInternetFragment extends Fragment {

        private Context context;

        public NoInternetFragment() {
            super();
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_no_internet, container, false);
            context = rootView.getContext();

            ((TextView) rootView.findViewById(R.id.check_your_connection)).setTypeface(Papaya.robotoLight);

            final Button tryAgainButton = (Button) rootView.findViewById(R.id.try_again);
            tryAgainButton.setTypeface(Papaya.robotoLight);
            tryAgainButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (Papaya.isOnline(context)) {
                        ((LoginActivity) getActivity()).showLogin();
                    } else {
                        ((LoginActivity) getActivity()).showNoInternet();
                    }
                }
            });

            return rootView;
        }
    }
}
