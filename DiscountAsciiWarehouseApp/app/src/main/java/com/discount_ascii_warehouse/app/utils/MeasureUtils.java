package com.discount_ascii_warehouse.app.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by mariobraga on 12/23/15.
 */
public class MeasureUtils {

    public static int convertDpToPixels(Context context ,int dp)
    {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float logicalDensity = displayMetrics.density;
        int px = (int) Math.ceil(dp * logicalDensity);

        return  px;

    }

    public static int convertPixelsToDP(Context context ,int px)
    {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float logicalDensity = displayMetrics.density;
        int dp = (int) Math.ceil(px / logicalDensity);

        return  dp;

    }
}
