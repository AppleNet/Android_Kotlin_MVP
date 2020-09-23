package com.example.llc.skin_lib;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * com.example.llc.skin_lib.SkinPreference
 * @author liulongchao
 * @since 2020-09-23
 *
 *  用来记录使用的那份皮肤
 * */
public class SkinPreference {

    private static final String SKIN_SHARED = "skins";
    private static final String KEY_SKIN_PATH = "skin-path";
    private final SharedPreferences mPref;

    private static volatile SkinPreference instance;

    public static void init(Context context) {
        if (instance == null) {
            synchronized (SkinPreference.class) {
                if (instance == null) {
                    instance = new SkinPreference(context);
                }
            }
        }
    }

    private SkinPreference(Context context) {
        mPref = context.getSharedPreferences(SKIN_SHARED, Context.MODE_WORLD_WRITEABLE);
    }

    public static SkinPreference getInstance() {
        return instance;
    }

    public void setSkin(String skinPath) {
        mPref.edit().putString(KEY_SKIN_PATH, skinPath).apply();
    }

    public void reset() {
        mPref.edit().remove(KEY_SKIN_PATH).apply();
    }

    public String getSkin() {
        return mPref.getString(KEY_SKIN_PATH, null);
    }

}
