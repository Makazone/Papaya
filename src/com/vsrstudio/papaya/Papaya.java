package com.vsrstudio.papaya;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import com.parse.Parse;

public class Papaya {

    /**
     * PARSE SET UP METHODS
     */

    private static boolean wasSetUp = false;

    public static void setUpParse(Context context) {
        if (!wasSetUp) {
            Parse.initialize(context, "5jOe Av4j5BCWsLxNrjicpDvnhnH5cyyds6X4n", "gKJOrNRPpxW9i4lyWwaVog3apmaNsI3HR02sft4k");
            wasSetUp = true;
        }
    }

    /**
     * PARSE METHODS END *
     */

    public static Typeface robotoSlabRegular;
    public static Typeface robotoSlabLight;
    public static Typeface robotoLight;

    public static void initializeFonts(Context context) {
        AssetManager assetManager = context.getAssets();
        robotoSlabRegular = Typeface.createFromAsset(assetManager, "RobotoSlab-Regular.ttf");
        robotoSlabLight = Typeface.createFromAsset(assetManager, "RobotoSlab-Light.ttf");
        robotoLight = Typeface.createFromAsset(assetManager, "Roboto-Light.ttf");
    }

}
