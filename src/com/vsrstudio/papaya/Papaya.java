package com.vsrstudio.papaya;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import com.parse.Parse;
import com.parse.ParseObject;
import com.vsrstudio.papaya.model.Book;
import com.vsrstudio.papaya.model.Request;
//import com.vsrstudio.papaya.model.User;

public class Papaya {

    /**
     * PARSE SET UP METHODS
     */

    private static boolean wasSetUp = false;

    public static void setUpParse(Context context) {
        if (!wasSetUp) {
            // Register parse object subclasses
//            ParseObject.registerSubclass(User.class);
            ParseObject.registerSubclass(Book.class);
            ParseObject.registerSubclass(Request.class);

            Parse.initialize(context, "CWpI83oY22MKeVOS2hLoIR8ZW80RO0DjTQHjtRps", "Q9NsuN0NW1kcqjPf0xyLyDBW2HgmsXrVeVRP8cjA");
            wasSetUp = true;
        }
    }

    /**
     * PARSE METHODS END *
     */

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static Typeface robotoSlabRegular;
    public static Typeface robotoSlabLight;
    public static Typeface robotoLight;

    public static void initializeFonts(Context context) {
        AssetManager assetManager = context.getAssets();
        robotoSlabRegular = Typeface.createFromAsset(assetManager, "RobotoSlab-Regular.ttf");
        robotoSlabLight = Typeface.createFromAsset(assetManager, "RobotoSlab-Light.ttf");
        robotoLight = Typeface.createFromAsset(assetManager, "Roboto-Light.ttf");
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
