package com.example.kotlin.plugin;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class PluginHook {

    private static final String TARGET_INTENT = "target_intent";

    public static void hookAMS() {

        // 动态代理需要替换的是IActivityManager对象
        try {
            // 1. Singleton对象
            Field iActivityManagerSingletonField;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                Class<?> clazz = Class.forName("android.app.ActivityManager");
                iActivityManagerSingletonField = clazz.getDeclaredField("IActivityManagerSingleton");
            } else {
                // 适配8.0之前版本
                Class<?> clazz = Class.forName("android.app.ActivityManagerNative");
                iActivityManagerSingletonField = clazz.getDeclaredField("gDefault");
            }

            iActivityManagerSingletonField.setAccessible(true);
            Object singleton = iActivityManagerSingletonField.get(null);

            // 2. mInstance对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            final Object mInstance = mInstanceField.get(singleton);

            Class<?> iActivityManagerClass = Class.forName("android.app.IActivityManager");
            Object mInstanceProxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{iActivityManagerClass}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    // 只有startActivity方法才处理
                    if ("startActivity".equals(method.getName())) {
                        // 修改Intent
                        //
                        int index = 0;
                        for (int i = 0; i < args.length; i++) {
                           if (args[i] instanceof Intent) {
                              index = i;
                              break;
                           }
                        }
                        // 获取启动插件的Intent
                        if (args[index] instanceof Intent) {
                            Intent intent = (Intent) args[index];
                            // 改成启动代理的Intent
                            Intent intentProxy = new Intent();
                            intentProxy.setComponent(new ComponentName("com.example.llcgs.android_kotlin", "com.example.llcgs.android_kotlin.replugin.proxy.ProxyActivity"));
                            intentProxy.putExtra(TARGET_INTENT, intent);

                            args[index] = intentProxy;
                        }
                    }
                    return method.invoke(mInstance, args);
                }
            });

            // 用代理对象替换系统的IActivityManager对象 ---> field
            mInstanceField.set(singleton, mInstanceProxy);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void hookHandler() {
        // 用创建的callback对象替换系统ActivityThread中Handler的callback对象
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            currentActivityThreadField.setAccessible(true);
            Object activityThread = currentActivityThreadField.get(null);

            // Handler对象
            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            Handler handler = (Handler) mHField.get(activityThread);

            // callback 属性
            Class<?> handlerClass = Class.forName("android.os.Handler");
            Field mCallbackField = handlerClass.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);

            Handler.Callback callback = new Handler.Callback() {

                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what) {
                        case 100:
                            // 拿到message
                            try {
                                // ActivityClientRecord === msg.obj
                                Field intentField = msg.obj.getClass().getDeclaredField("intent");
                                intentField.setAccessible(true);
                                // 启动代理的intent
                                Intent intentProxy = (Intent) intentField.get(msg.obj);
                                // 获取启动插件的intent
                                Intent intent = intentProxy.getParcelableExtra(TARGET_INTENT);
                                if (intent != null) {
                                    intentField.set(msg.obj, intent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 159:
                            // ClientTransaction === msg.obj
                            Class<?> transactionClass = msg.obj.getClass();
                            try {
                                Field mActivityCallbacksField = transactionClass.getDeclaredField("mActivityCallbacks");
                                mActivityCallbacksField.setAccessible(true);

                                List list = (List) mActivityCallbacksField.get(msg.obj);
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).getClass().getName()
                                            .equals("android.app.servertransaction.LaunchActivityItem")) {
                                        Object launchActivityItem = list.get(i);
                                        Field mIntentField = launchActivityItem.getClass().getDeclaredField("mIntent");
                                        mIntentField.setAccessible(true);

                                        Intent intentProxy = (Intent) mIntentField.get(launchActivityItem);
                                        Intent intent = intentProxy.getParcelableExtra(TARGET_INTENT);
                                        if (intent != null) {
                                            mIntentField.set(launchActivityItem, intent);
                                        }
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    // 这里一定要return false，否则会替换失败
                    return false;
                }
            };

            mCallbackField.set(handler, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
