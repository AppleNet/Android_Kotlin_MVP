package com.example.llcgs.android_kotlin.utils;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

/**
 *  com.example.llcgs.android_kotlin.utils.InjectUtils
 * @author liulongchao
 * @since 2020-08-03
 * */
public class InjectUtils {


    public static void injectViews(Activity activity) {

        Class<? extends Activity> activityClass = activity.getClass();
        // 获得此类所有的成员
        Field[] declaredFields = activityClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            // 判断属性是否被 @InjectView 标记
            if (declaredField.isAnnotationPresent(InjectView.class)) {
                InjectView injectViewAnnotation = declaredField.getAnnotation(InjectView.class);
                // 获取注解中设置的 id
                int valueResId = injectViewAnnotation.value();
                View view = activity.findViewById(valueResId);
                // 反射设置属性的值
                declaredField.setAccessible(true);
                try {
                    declaredField.set(activity, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
