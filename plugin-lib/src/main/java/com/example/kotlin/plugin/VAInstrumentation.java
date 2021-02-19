package com.example.kotlin.plugin;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * com.example.kotlin.plugin.VAInstrumentation
 *
 * @author liulongchao
 * @since 2021/2/18
 *
 * 参考滴滴的 VirtualAPK 的 hook Instrumentation
 */
public class VAInstrumentation extends Instrumentation implements Handler.Callback {

    public static final String TAG = "VAInstrumentation";
    public static final int LAUNCH_ACTIVITY = 100;

    /**
     * 原始的 Instrumentation
     */
    protected Instrumentation mBase;
    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 存储插件activity
     */
    protected final ArrayList<WeakReference<Activity>> mActivities = new ArrayList<>();

    /**
     * 构造方法
     *
     * @param context context
     * @param base base
     */
    public VAInstrumentation(Context context, Instrumentation base) {
        this.mBase = base;
        this.mContext = context;
    }

    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target, Intent intent, int requestCode) {
        injectIntent(intent);
        // 由于这个方法是隐藏的,因此需要使用反射调用;首先找到这个方法
        // execStartActivity有重载，别找错了
        try {
            @SuppressLint("PrivateApi")
            Method execStartActivity = Instrumentation.class.getDeclaredMethod("execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class);
            execStartActivity.setAccessible(true);
            return (ActivityResult) execStartActivity.invoke(mBase, who,
                    contextThread, token, target, intent, requestCode);
        } catch (Exception e) {
            throw new RuntimeException("do not support!!! pls adapt it");
        }
    }

    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target, Intent intent, int requestCode, Bundle options) {
        injectIntent(intent);
        try {
            @SuppressLint("PrivateApi")
            Method execStartActivity = Instrumentation.class.getDeclaredMethod("execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return (ActivityResult) execStartActivity.invoke(mBase, who,
                    contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            throw new RuntimeException("do not support!!! pls adapt it");
        }
    }

    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Fragment target, Intent intent, int requestCode, Bundle options) {
        injectIntent(intent);
        try {
            @SuppressLint("PrivateApi")
            Method execStartActivity = Instrumentation.class.getDeclaredMethod("execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Fragment.class, Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return  (ActivityResult) execStartActivity.invoke(mBase, who, contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            throw new RuntimeException("do not support!!! pls adapt it");
        }
    }

    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, String target, Intent intent, int requestCode, Bundle options) {
        injectIntent(intent);
        try {
            @SuppressLint("PrivateApi")
            Method execStartActivity = Instrumentation.class.getDeclaredMethod("execStartActivity",
                    Context.class, IBinder.class, IBinder.class, String.class, Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return  (ActivityResult) execStartActivity.invoke(mBase, who, contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            throw new RuntimeException("do not support!!! pls adapt it");
        }
    }

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return mBase.newApplication(cl, className, context);
    }

    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        injectActivity(activity);
        mBase.callActivityOnCreate(activity, icicle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle, PersistableBundle persistentState) {
        injectActivity(activity);
        mBase.callActivityOnCreate(activity, icicle, persistentState);
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        try {
            cl.loadClass(className);
            Log.i(TAG, String.format("newActivity[%s]", className));
        } catch (ClassNotFoundException e) {
            ComponentName component;
            if (intent.getBooleanExtra("isPlugin", false)) {
                component = new ComponentName(intent.getStringExtra("packageName"), intent.getStringExtra("className"));
            } else {
                component = intent.getComponent();
            }

            if (component == null) {
                return newActivity(mBase.newActivity(cl, className, intent));
            }

            String targetClassName = component.getClassName();
            // 插件的 classLoader
            Activity activity = mBase.newActivity(mContext.getClassLoader(), targetClassName, intent);
            activity.setIntent(intent);

            // for 4.1+
            // Reflector.QuietReflector.with(activity).field("mResources").set(plugin.getResources());

            return newActivity(activity);
        }

        return newActivity(mBase.newActivity(cl, className, intent));
    }

    private void injectIntent(Intent intent) {
        ComponentName component = intent.getComponent();

        if (component != null) {
            Log.i(TAG, String.format("execStartActivity[%s : %s]", intent.getComponent().getPackageName(), intent.getComponent().getClassName()));
            String targetPackageName = component.getPackageName();
            String targetClassName = component.getClassName();
            if (!targetPackageName.equals(mContext.getPackageName())) {
                intent.putExtra("isPlugin", true);
                intent.putExtra("packageName", targetPackageName);
                intent.putExtra("className", targetClassName);
                intent.setClassName(mContext, "com.example.llcgs.android_kotlin.replugin.proxy.ProxyActivity");
            }
        }
    }

    protected void injectActivity(Activity activity) {
//        final Intent intent = activity.getIntent();
//        if (PluginUtil.isIntentFromPlugin(intent)) {
//            Context base = activity.getBaseContext();
//            try {
//                LoadedPlugin plugin = this.mPluginManager.getLoadedPlugin(intent);
//                Reflector.with(base).field("mResources").set(plugin.getResources());
//                Reflector reflector = Reflector.with(activity);
//                reflector.field("mBase").set(plugin.createPluginContext(activity.getBaseContext()));
//                reflector.field("mApplication").set(plugin.getApplication());
//
//                // set screenOrientation
//                ActivityInfo activityInfo = plugin.getActivityInfo(PluginUtil.getComponent(intent));
//                if (activityInfo.screenOrientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
//                    activity.setRequestedOrientation(activityInfo.screenOrientation);
//                }
//
//                // for native activity
//                ComponentName component = PluginUtil.getComponent(intent);
//                Intent wrapperIntent = new Intent(intent);
//                wrapperIntent.setClassName(component.getPackageName(), component.getClassName());
//                activity.setIntent(wrapperIntent);
//
//            } catch (Exception e) {
//                Log.w(TAG, e);
//            }
//        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == LAUNCH_ACTIVITY) {
            Object r = msg.obj;
            try {
                Class<?> rClass = r.getClass();
                Field intentField = rClass.getDeclaredField("intent");
                intentField.setAccessible(true);
                Intent intent = (Intent) intentField.get(r);
                intent.setExtrasClassLoader(mContext.getClassLoader());
                Field activityInfoField = rClass.getDeclaredField("activityInfo");
                activityInfoField.setAccessible(true);
                ActivityInfo activityInfo = (ActivityInfo) activityInfoField.get(r);
                activityInfo.theme = 0x1234;
            } catch (Exception e) {
                Log.w(TAG, e);
            }
        }

        return false;
    }

    @Override
    public Context getContext() {
        return mBase.getContext();
    }

    @Override
    public Context getTargetContext() {
        return mBase.getTargetContext();
    }

    @Override
    public ComponentName getComponentName() {
        return mBase.getComponentName();
    }

    protected Activity newActivity(Activity activity) {
        synchronized (mActivities) {
            for (int i = mActivities.size() - 1; i >= 0; i--) {
                if (mActivities.get(i).get() == null) {
                    mActivities.remove(i);
                }
            }
            mActivities.add(new WeakReference<>(activity));
        }
        return activity;
    }

    List<WeakReference<Activity>> getActivities() {
        synchronized (mActivities) {
            return new ArrayList<>(mActivities);
        }
    }
}


