package com.jdqm.imageloader.util;

import android.content.res.Resources;


/**
 * Created by Jdqm on 2018-3-30.
 */

public class DisplayUtil {

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int dpToPixel(float dpValue) {
        return (int) (Resources.getSystem().getDisplayMetrics().density * dpValue);
    }
}
