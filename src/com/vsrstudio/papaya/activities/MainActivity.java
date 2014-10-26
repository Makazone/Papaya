package com.vsrstudio.papaya.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.vsrstudio.papaya.Papaya;
import com.vsrstudio.papaya.R;
import com.vsrstudio.papaya.fragments.*;
import com.vsrstudio.papaya.model.Book;
import com.vsrstudio.papaya.model.GoogleCallback;
import com.vsrstudio.papaya.model.Request;
import com.vsrstudio.papaya.model.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private int position = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_show_menu,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                selectItem(position);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        setUpDrawer();
        setUpActionBar();

        if (savedInstanceState == null) {
            setItemChecked(findViewById(R.id.item_search));
            selectItem(1);
        }

//        Book a = new Book("testBook2", "2", "History", "none", "none", 5);
//        a.setOwner(User.currentUser.getParseUser());
//        a.saveInBackground();
//
//        Book b = new Book("testBook2.2", "3", "Kid books", "none", "none", 1);
//        b.setOwner(User.currentUser.getParseUser());
//        b.saveInBackground();

//        final Book b = ParseObject.createWithoutData(Book.class, "MApkGotOo9");
//
//        // getUserBooks
//        User currentUser = User.currentUser;
//        currentUser.findUserBooks(new FindCallback<Book>() {
//            @Override
//            public void done(List<Book> list, ParseException e) {
//                Request.makeRequest(list, b, Request.ALL, 0);
//            }
//        });

//        try {
//            Book.findBooksByString("Java", new GoogleCallback<Book>() {
//                @Override
//                public void completedGoogleTask(ArrayList<Book> list) {
//
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

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

        final ImageButton showMenuButton = (ImageButton) findViewById(R.id.show_menu);
        showMenuButton.setVisibility(View.VISIBLE);
        showMenuButton.setOnClickListener(this);
    }

    private void setUpDrawer() {
        final TextView profileItem = (TextView) findViewById(R.id.item_profile);
        profileItem.setTypeface(Papaya.robotoSlabLight);
        profileItem.setText(User.currentUser.getParseUser().getUsername());
        profileItem.setOnClickListener(this);

        final TextView searchItem = (TextView) findViewById(R.id.item_search);
        searchItem.setTypeface(Papaya.robotoSlabLight);
        searchItem.setOnClickListener(this);

        final TextView lastItem = (TextView) findViewById(R.id.item_last);
        lastItem.setTypeface(Papaya.robotoSlabLight);
        lastItem.setOnClickListener(this);

        final TextView myBooksItem = (TextView) findViewById(R.id.item_my_books);
        myBooksItem.setTypeface(Papaya.robotoSlabLight);
        myBooksItem.setOnClickListener(this);

        final TextView settingsItem = (TextView) findViewById(R.id.item_settings);
        settingsItem.setTypeface(Papaya.robotoSlabLight);
        settingsItem.setOnClickListener(this);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment;
        String title;

        switch (position) {
            case 0:
                fragment = new ProfileFragment();
                title = getResources().getString(R.string.profile);
                break;
            case 1:
                fragment = new SearchFragment();
                title = getResources().getString(R.string.search);
                break;
            case 2:
                fragment = new LastFragment();
                title = getResources().getString(R.string.last);
                break;
            case 3:
                fragment = new MyBooksFragment();
                title = getResources().getString(R.string.my_books);
                break;
            case 4:
                fragment = new SettingsFragment();
                title = getResources().getString(R.string.settings);
                break;
            default:
                fragment = new SearchFragment();
                title = getResources().getString(R.string.search);
        }

        setTitle(title);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_profile:
                setItemChecked(view);
                position = 0;
                mDrawerLayout.closeDrawer(findViewById(R.id.left_drawer));
                break;
            case R.id.item_search:
                setItemChecked(view);
                position = 1;
                mDrawerLayout.closeDrawer(findViewById(R.id.left_drawer));
                break;
            case R.id.item_last:
                setItemChecked(view);
                position = 2;
                mDrawerLayout.closeDrawer(findViewById(R.id.left_drawer));
                break;
            case R.id.item_my_books:
                setItemChecked(view);
                position = 3;
                mDrawerLayout.closeDrawer(findViewById(R.id.left_drawer));
                break;
            case R.id.item_settings:
                setItemChecked(view);
                position = 4;
                mDrawerLayout.closeDrawer(findViewById(R.id.left_drawer));
                break;
            case R.id.show_menu:
                if (mDrawerLayout.isDrawerOpen(findViewById(R.id.left_drawer))) {
                    mDrawerLayout.closeDrawer(findViewById(R.id.left_drawer));
                } else {
                    setTitle(getString(R.string.app_name));
                    mDrawerLayout.openDrawer(findViewById(R.id.left_drawer));
                }
                break;
        }
    }

    private void setItemChecked(View view) {
        final TextView profileItem = (TextView) findViewById(R.id.item_profile);
        profileItem.setTypeface(Papaya.robotoSlabLight);
        profileItem.setActivated(false);

        final TextView searchItem = (TextView) findViewById(R.id.item_search);
        searchItem.setTypeface(Papaya.robotoSlabLight);
        searchItem.setActivated(false);

        final TextView lastItem = (TextView) findViewById(R.id.item_last);
        lastItem.setTypeface(Papaya.robotoSlabLight);
        lastItem.setActivated(false);

        final TextView myBooksItem = (TextView) findViewById(R.id.item_my_books);
        myBooksItem.setTypeface(Papaya.robotoSlabLight);
        myBooksItem.setActivated(false);

        final TextView settingsItem = (TextView) findViewById(R.id.item_settings);
        settingsItem.setTypeface(Papaya.robotoSlabLight);
        settingsItem.setActivated(false);

        view.setActivated(true);
        ((TextView) view).setTypeface(Papaya.robotoSlabRegular);
    }

    public void setTitle(String title) {
        ((TextView) findViewById(R.id.title)).setText(title);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
