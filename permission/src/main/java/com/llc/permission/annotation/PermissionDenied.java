package com.llc.permission.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * com.llc.permission.annonation.Permission
 *
 * @author liulongchao
 * @since 2021/2/20
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionDenied {

    int requestCode() default -1;
}
