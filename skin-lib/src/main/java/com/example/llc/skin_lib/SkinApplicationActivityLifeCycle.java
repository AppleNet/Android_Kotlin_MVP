package com.example.llc.skin_lib;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.ArrayMap;
import android.view.LayoutInflater;

import com.example.llc.skin_lib.utils.SkinThemeUtils;

import java.lang.reflect.Field;
import java.util.Observable;

/**
 * com.example.llc.skin_lib.SkinApplicationActivityLifeCycle
 * @author liulongchao
 * @since 2020-09-24
 * */
public class SkinApplicationActivityLifeCycle implements Application.ActivityLifecycleCallbacks {

    private Observable mObservable;
    private ArrayMap<Activity, SkinLayoutInflaterFactory> mLayoutInflaterFactory = new ArrayMap<>();

    public SkinApplicationActivityLifeCycle(Observable observable) {
        this.mObservable = observable;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        /*
         * 更新状态栏
         * */
        SkinThemeUtils.updateStatusBarColor(activity);
        /*
         * 更新布局视图
         * */
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        try {
            // Android布局加载器 使用 mFactorySet 标记是否设置过 Factory
            // 如设置过 抛出一次
            // 设置 mFactorySet 为 false
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(layoutInflater, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SkinLayoutInflaterFactory skinLayoutInflaterFactory = new SkinLayoutInflaterFactory(activity);
        LayoutInflaterCompat.setFactory2(layoutInflater, skinLayoutInflaterFactory);
        mLayoutInflaterFactory.put(activity, skinLayoutInflaterFactory);
        mObservable.addObserver(skinLayoutInflaterFactory);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        SkinLayoutInflaterFactory skinLayoutInflaterFactory = mLayoutInflaterFactory.get(activity);
        SkinManager.getInstance().deleteObserver(skinLayoutInflaterFactory);
    }
}
