package com.llc.permission.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import java.lang.reflect.Method;

/**
 * com.llc.permission.utils.PermissionUtils
 *
 * @author liulongchao
 * @since 2021/2/20
 */
public class PermissionUtils {

    /**
     * TODO 检查是否需要去请求权限，此方法目的：就是检查 是否已经授权了
     *
     * @param context      context
     * @param permissions  permissions
     * @return 返回false代表需要请求权限，  返回true代表不需要请求权限 就可以结束PermissionActivity了
     */
    public static boolean hasPermissionRequest(Context context, String... permissions) {
        for (String permission : permissions) {
            if (!isPermissionRequest(context, permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * TODO 判断参数中传递进去的权限是否已经被授权了
     *
     * @param context  context
     * @param permission permission
     * @return 返回false代表需要请求权限，  返回true代表不需要请求权限 就可以结束MyPermisisonActivity了
     */
    private static boolean isPermissionRequest(Context context, String permission) {
        try {
            int checkSelfPermission = ContextCompat.checkSelfPermission(context, permission);
            return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
        } catch (Exception e) {
            return false;
        }
    }

    // TODO 最后判断下 是否真正的成功
    /**
     * @param gantedResult gantedResult
     * @return 权限组申请成功
     */
    public static boolean requestPermissionSuccess(int... gantedResult) {
        if (gantedResult == null || gantedResult.length <= 0) {
            return false;
        }

        for (int permissionValue : gantedResult) {
            if (permissionValue != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    // TODO 说白了：就是用户被拒绝过一次，然后又弹出这个框，【需要给用户一个解释，为什么要授权，就需要执行此方法判断】
    // 当用户点击了不再提示，这种情况要考虑到才行
    /**
     * @param activity activity
     * @param permissions permissions
     * @return 是否展示UI
     */
    public static boolean shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }

    // TODO 专门去 回调 MainActivity (被@PermissionCancel/PermissionDenied) 的 函数
    public static void invokeAnnotation(Object object, Class annotationClass) {
        Class<?> objectClass = object.getClass(); // 可能是 MainActivity

        // 遍历所有函数
        Method[] methods = objectClass.getDeclaredMethods();
        for (Method method : methods) {
            // 让虚拟机不要去检测 private
            method.setAccessible(true);
            // 判断是否被 annotationClass 注解过的函数
            boolean annotationPresent = method.isAnnotationPresent(annotationClass);
            if (annotationPresent) {
                // 当前方法 annotationClass 注解过的函数
                try {
                    method.invoke(object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
