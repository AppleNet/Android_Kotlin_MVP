package com.example.kotlin.hotfix;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class ShareReflectUtils {

    /**
     *  反射获取属性
     *
     *
     * */
    static Field findFiled(Object instance, String name) {

        Class<?> aClass = instance.getClass();
        while (aClass != Object.class) {
            try {
                Field field = aClass.getDeclaredField(name);
                if (field != null) {
                    field.setAccessible(true);
                    return field;
                }

            } catch (NoSuchFieldException e) {
                //
            }

            aClass = aClass.getSuperclass();
        }
        throw new RuntimeException(name + " field not found");
    }


    static Method findMethod(Object instance, String name, Class<?>... parameterTypes) {

        Class<?> aClass = instance.getClass();
        while (aClass != Object.class) {
            try {
                Method method = aClass.getDeclaredMethod(name, parameterTypes);
                if (method != null) {
                    method.setAccessible(true);
                    return method;
                }

            } catch (NoSuchMethodException e) {
                //
            }

            aClass = aClass.getSuperclass();
        }
        throw new RuntimeException(name + " method not found");
    }
}
