package com.example.llc.skin_lib.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * com.example.llc.skin_lib.utils.SkinResources
 * @author liulongchao
 * @since 2020-09-23
 *
 *  换肤用的资源
 * */
public class SkinResources {

    // 原始 app 使用的 resources
    private Resources mAppResources;
    // 插件 apk 中的 resources
    private Resources mSkinResources;

    private String mSkinPkgName;
    private boolean isDefaultSkin = true;

    private volatile static SkinResources instance;

    public static void init(Context context) {
        if (instance == null) {
            synchronized (SkinResources.class) {
                if (instance == null) {
                    instance = new SkinResources(context);
                }
            }
        }
    }

    private SkinResources(Context context) {}

    public static SkinResources getInstance() {
        return instance;
    }

    public void reset() {
        mAppResources = null;
        mSkinPkgName = "";
        isDefaultSkin = true;
    }

    public void applySkin(Resources resources, String pkgName) {
        mSkinResources = resources;
        mSkinPkgName = pkgName;
        isDefaultSkin = TextUtils.isEmpty(pkgName) || resources == null;
    }

    /**
     *  1. 通过原始 app 中的 resId(R.id.XX) 获取到自己的名字
     *  2. 根据名字和类型获取皮肤包中的 ID
     *
     * @param resId resId
     * */
    private int getIdentifier(int resId) {
        if (isDefaultSkin) {
            return resId;
        }
        //
        String resName = mAppResources.getResourceEntryName(resId);
        String resType = mAppResources.getResourceTypeName(resId);

        return mSkinResources.getIdentifier(resName, resType, mSkinPkgName);
    }

    /**
     *  获取颜色值
     *
     * @param resId resId
     * */
    public int getColor(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColor(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColor(resId);
        }

        return mSkinResources.getColor(resId);
    }

    public ColorStateList getColorStateList(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColorStateList(resId);
        }
        return mSkinResources.getColorStateList(resId);
    }

    /**
     *  获取 drawable
     *
     * @param resId resId
     *
     * */
    public Drawable getDrawable(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId);
        }

        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getDrawable(resId);
        }

        return mSkinResources.getDrawable(resId);
    }

    /**
     *  可能是颜色值，可能是 drawable
     *
     * @param resId resId
     *
     * */
    public Object getBackground(int resId) {
        String resourceTypeName = mAppResources.getResourceTypeName(resId);
        if ("color".equals(resourceTypeName)) {
            return getColor(resId);
        } else {
            return getDrawable(resId);
        }
    }

}
