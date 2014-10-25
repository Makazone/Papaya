package com.vsrstudio.papaya;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

public class Papaya {

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
