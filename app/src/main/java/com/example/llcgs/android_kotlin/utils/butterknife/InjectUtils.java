package com.example.llcgs.android_kotlin.utils.butterknife;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *  com.example.llcgs.android_kotlin.utils.butterknife.InjectUtils
 * @author liulongchao
 * @since 2020-08-03
 * */
public class InjectUtils {

    public static void injectViews(Activity activity) {

        int valueResId;
        View view;
        Class<? extends Activity> activityClass = activity.getClass();
        // 获得此类所有的成员
        Field[] declaredFields = activityClass.getDeclaredFields();
        Method[] declaredMethods = activityClass.getDeclaredMethods();

        for (Field declaredField : declaredFields) {
            // 判断属性是否被 @InjectView 标记
            if (declaredField.isAnnotationPresent(InjectView.class)) {
                InjectView injectViewAnnotation = declaredField.getAnnotation(InjectView.class);
                // 获取注解中设置的 id
                valueResId = injectViewAnnotation.value();
                for (Method declaredMethod : declaredMethods) {
                    if (declaredMethod.isAnnotationPresent(InjectClick.class)) {
                        InjectClick annotation = declaredMethod.getAnnotation(InjectClick.class);
                        int[] value = annotation.value();
                        for (int i : value) {
                            if (i == valueResId) {
                                // 同一个 id
                                view = activity.findViewById(valueResId);

                                Object object = Proxy.newProxyInstance(activityClass.getClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
                                    @Override
                                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                        // onclick 的时候
                                        return declaredMethod.invoke(activity, args);
                                    }
                                });
                                // 设置 onClick 事件
                                view.setOnClickListener((View.OnClickListener) object);

                                // 反射设置属性的值
                                declaredField.setAccessible(true);
                                try {
                                    declaredField.set(activity, view);
                                } catch (IllegalAccessException e) {
                                    //
                                }
                            }
                        }
                    }
                }

            }
        }
    }

}
