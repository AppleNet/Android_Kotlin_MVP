package com.example.llc.skin_lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;

public class SkinThemeUtils {

    private static final int[] APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS = {android.R.attr.colorPrimaryDark};
    private static final int[] STATUS_BAR_COLOR_ATTRS = {android.R.attr.statusBarColor, android.R.attr.navigationBarColor};

    /**
     *  获得 Theme 中属性中定义的资源 id
     *
     * @param context context
     * @param attrs attrs
     * @return int[]
     * */
    public static int[] getResId(Context context, int[] attrs) {
        int[] resIds = new int[attrs.length];
        TypedArray a = context.obtainStyledAttributes(attrs);
        for (int i = 0; i < attrs.length; i++) {
            resIds[i] = a.getResourceId(i, 0);
        }
        a.recycle();
        return resIds;
    }

    /**
     *  更新导航栏，状态栏的颜色值
     *
     * @param activity  activity 当前页面
     *
     * */
    public static void updateStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        // 获得 statusBarColor 与 navigationBarColor
        // 当与 colorPrimaryDark 不同时 以 statusBarColor 为准
        int[] resIds = getResId(activity, STATUS_BAR_COLOR_ATTRS);
        int statusBarColorResId = resIds[0];
        int navigationBarColor = resIds[1];

        // 如果直接在style中写入固定颜色值(而不是 @color/XXX ) 获得0
        if (statusBarColorResId != 0) {
            int color = SkinResources.getInstance().getColor(statusBarColorResId);
            activity.getWindow().setStatusBarColor(color);
        } else {
            int colorPrimaryDarkResId = getResId(activity, APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS)[0];
            if (colorPrimaryDarkResId != 0) {
                int color = SkinResources.getInstance().getColor(colorPrimaryDarkResId);
                activity.getWindow().setStatusBarColor(color);
            }
        }

        if (navigationBarColor != 0) {
            int color = SkinResources.getInstance().getColor(navigationBarColor);
            activity.getWindow().setNavigationBarColor(color);
        }

    }
}
