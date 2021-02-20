package com.llc.permission.aspect;

import android.app.Fragment;
import android.content.Context;

import com.llc.permission.activity.PermissionActivity;
import com.llc.permission.annotation.Permission;
import com.llc.permission.annotation.PermissionCancel;
import com.llc.permission.annotation.PermissionDenied;
import com.llc.permission.core.IPermission;
import com.llc.permission.utils.PermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * com.llc.permission.aspect.PermissionAspect
 *
 * @author liulongchao
 * @since 2021/2/20
 */
@Aspect
public class PermissionAspect {

    /**
     *  切点函数
     *
     * */
    @Pointcut("execution(@com.llc.permission.annotation.Permission * *(..)) && @annotation(permission)")
    public void pointActionMethod(Permission permission){}

    /**
     *  切面函数
     *
     * */
    @Around("pointActionMethod(permission)")
    public void proceedingJoinPoint(final ProceedingJoinPoint point, final Permission permission) throws Exception {
        Context context = null;
        final Object thisObject = point.getThis(); // thisObject == null 环境有问题
        // context初始化
        if (thisObject instanceof Context) {
            context = (Context) thisObject;
        } else if (thisObject instanceof Fragment) {
            context = ((Fragment) thisObject).getActivity();
        }

        // 判断是否为null
        if (null == context || permission == null) {
            throw new IllegalAccessException("null == context || permission == null is null");
        }

        final Context finalContext = context;
        //
        PermissionActivity.requestPermissionAction(finalContext, permission.value(), permission.requestCode(), new IPermission() {
            @Override
            public void ganted() {
                try {
                    point.proceed();
                } catch (Throwable throwable) {
                    //
                }
            }

            @Override
            public void cancel() {
                PermissionUtils.invokeAnnotation(finalContext, PermissionCancel.class);
            }

            @Override
            public void denied() {
                PermissionUtils.invokeAnnotation(finalContext, PermissionDenied.class);
            }
        });
    }
}
