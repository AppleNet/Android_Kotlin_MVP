package com.example.llcgs.android_kotlin.customview.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {

    public static float dp2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, Resources.getSystem().getDisplayMetrics());
    }

}
