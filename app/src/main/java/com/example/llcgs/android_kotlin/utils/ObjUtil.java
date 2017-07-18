package com.example.llcgs.android_kotlin.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 作者：Alex
 * 时间：2016年09月03日    10:09
 * 简述：
 */
@SuppressWarnings({"unused", "unchecked", "SimplifiableIfStatement", "ManualArrayToCollectionCopy"})
public class ObjUtil {
    /**
     * 对象为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return (((CharSequence) obj).length() <= 0);
        }
        if (obj instanceof File) {
            return ((File) obj).length() <= 0;
        }
        if (obj instanceof Map) {
            return ((Map) obj).size() <= 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).size() <= 0;
        }
        if (obj.getClass().isArray()) {
            return (Array.getLength(obj) == 0);
        }
        if (obj instanceof Object[]) {
            return (((Object[]) obj).length == 0);
        }
        if (obj instanceof Bitmap) {
            return isBitmapEmpty((Bitmap) obj);
        }
        if (obj instanceof Drawable) {
            return isDrawableEmpty((Drawable) obj);
        }
        if (obj instanceof JSONObject) {
            return ((JSONObject) obj).length() <= 0;
        }
        if (obj instanceof JSONArray) {
            return ((JSONArray) obj).length() <= 0;
        }
        return ((obj.toString()).length() <= 0);
    }

    /**
     * 对象非空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    private static boolean isBitmapEmpty(Bitmap bitmap) {
        return (bitmap == null) || (bitmap.getHeight() <= 0) || (bitmap.getWidth() <= 0);
    }

    private static boolean isDrawableEmpty(Drawable drawable) {
        if ((drawable == null)) {
            return true;
        } else if ((0 >= drawable.getIntrinsicHeight())) {
            return true;
        } else if ((0 >= drawable.getIntrinsicWidth())) {
            return true;
        }
        return false;
    }

    public static String toString(List<?> list, String split) {
        StringBuilder builder = new StringBuilder();
        builder.append("");
        for (int i = 0; (list != null) && (i < list.size()); i++) {
            if (i < (list.size() - 1)) {
                builder.append(list.get(i)).append(split);
            } else {
                builder.append(list.get(i)).append("");
            }
        }
        return builder.toString();
    }

    public static String[] toStringArray(List<?> list) {
        if ((list == null) || (list.size() <= 0)) {
            return new String[0];
        }
        String array[] = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i) + "";
        }
        return array;
    }


    /**
     * 数组 转 List（实质上还是 ArayList）
     */
    @SuppressWarnings("UseBulkOperation")
    @SafeVarargs
    public static <T> List<T> toList(T... t) {
        if ((t == null) || (t.length <= 0)) {
            return new ArrayList();
        }
        List<T> list = new ArrayList();
        for (T aT : t) {
            list.add(aT);
        }
        return list;
    }

    /**
     * 数组 转 ArrayList
     */
    @SuppressWarnings({"SingleStatementInBlock", "UseBulkOperation"})
    @SafeVarargs
    public static <T> ArrayList<T> toArrayList(T... t) {
        if ((t == null) || (t.length <= 0)) {
            return new ArrayList();
        }
        ArrayList<T> list = new ArrayList();
        for (T aT : t) {
            list.add(aT);
        }
        return list;
    }

    /**
     * 是java 原生类 类型
     */
    public static boolean isJavaOriginalClass(Object obj) {
        if (obj instanceof String) {
            return true;
        }
        if (obj instanceof Number) {
            return true;
        }
        if (obj instanceof Boolean) {
            return true;
        }
        if (obj instanceof Character) {
            return true;
        }
        return obj == null;
    }

    @SuppressWarnings({"SingleStatementInBlock", "SimplifiableIfStatement", "WeakerAccess"})
    public static boolean isNumber(Class clazz) {
        if (clazz == null) {
            return false;
        }
        if (Boolean.class.getSimpleName().equalsIgnoreCase(clazz.getSimpleName())) {
            return boolean.class.isAssignableFrom(clazz);
        }
        if (Integer.class.getSimpleName().equalsIgnoreCase(clazz.getSimpleName())) {
            return int.class.isAssignableFrom(clazz);
        }
        if (Double.class.getSimpleName().equalsIgnoreCase(clazz.getSimpleName())) {
            return double.class.isAssignableFrom(clazz);
        }
        if (Long.class.getSimpleName().equalsIgnoreCase(clazz.getSimpleName())) {
            return long.class.isAssignableFrom(clazz);
        }
        if (Float.class.getSimpleName().equalsIgnoreCase(clazz.getSimpleName())) {
            return float.class.isAssignableFrom(clazz);
        }
        if (Byte.class.getSimpleName().equalsIgnoreCase(clazz.getSimpleName())) {
            return byte.class.isAssignableFrom(clazz);
        }
        return false;
    }


    /**
     * 从 第0个 开始 ，向右取length个（最多length个）
     */
    public static <T> List<T> subList4Start(List<T> list, int length) {
        return subList4StartLength(list, 0, length);
    }

    /**
     * 从 第startIndex个 开始 ，向右取length个（最多length个）
     */
    @SuppressWarnings({"SameParameterValue", "WeakerAccess"})
    public static <T> List<T> subList4StartLength(List<T> list, int startIndex, int length) {
        if ((list == null) || (list.size() <= 0)) {
            return new ArrayList<>();
        }
        if (list.size() <= startIndex) {
            startIndex = list.size() - 1;
        }
        List<T> subList = new ArrayList();
        for (int i = startIndex; (i < list.size() && i <= length); i++) {
            subList.add(list.get(i));
        }
        return subList;
    }

    /**
     * 从 最后一个 开始 ，向左取length个（最多length个）
     */
    @SuppressWarnings("SingleStatementInBlock")
    public static <T> List<T> subList4End(List<T> list, int length) {
        //list.subList()
        if ((list == null) || (list.size() <= 0)) {
            return new ArrayList();
        }
        List<T> subList = new ArrayList();
        // 0 1 2 3 4 5 6 7 8
        //8 7 6 5
        //9 - 5 >= 4
        for (int i = list.size() - 1; (i >= 0) && (list.size() - i <= length); i--) {
            subList.add(list.get(i));
        }
        return subList;
    }

    /**
     * 从 第endIndex个 开始 ，向左取length个（最多length个）
     */
    public static <T> List<T> subList4EndLength(List<T> list, int endIndex, int length) {
        //list.subList()
        if ((list == null) || (list.size() <= 0)) {
            return new ArrayList();
        }
        if (endIndex >= list.size()) {
            endIndex = list.size() - 1;
        }
        List<T> subList = new ArrayList();
        // 0 1 2 3 4 5 6 7 8
        //8 7 6 5
        //8 - 5 >= 4
        for (int i = endIndex; (i >= 0) && (endIndex - i < length); i--) {
            subList.add(list.get(i));
        }
        return subList;
    }

    /**
     * copy一个完整的集合，得到一个ArrayList
     */
    @SuppressWarnings("UseBulkOperation")
    public static <T> List<T> subList4Full(List<T> list) {
        if ((list == null) || (list.size() <= 0)) {
            return new ArrayList();
        }
        List<T> subList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            subList.add(list.get(i));
        }
        return subList;
    }

    /**
     * 如果 startIndex 大于list.size 得到一个空集合
     * 如果 endIndex 大于list.size ，最多遍历到 list 结尾
     *
     * @param startIndex 下标， 包含
     * @param endIndex   下标， 包含
     */
    public static <T> List<T> subList(List<T> list, int startIndex, int endIndex) {
        if ((list == null) || (list.size() <= 0) || (startIndex > endIndex) || (startIndex >= list.size())) {
            return new ArrayList();
        }
        List<T> subList = new ArrayList();
        for (int i = startIndex; (i <= endIndex) && (i < list.size() - 1); i++) {
            subList.add(list.get(i));
        }
        return subList;
    }

    /**
     * 将 对象 所有的 非 static l类型 成员变量 置空
     */
    public static void setFieldNull(Object obj) {
        if (obj == null) {
            return;
        }
        Field[] declaredFieldArray = obj.getClass().getDeclaredFields();
        declaredFieldArray = (declaredFieldArray == null) ? new Field[0] : declaredFieldArray;
        for (Field field : declaredFieldArray) {
            int modifiers = field.getModifiers();
            field.setAccessible(true);
            if (!Modifier.isStatic(modifiers) && !isNumber(field.getType())) {
                try {
                    field.set(obj, null);
                    //LogTrack.e(field.getName() + " " + field.get(obj) + " " + field.getModifiers() + " " + Modifier.isStatic(field.getModifiers()));
                } catch (IllegalAccessException e) {
                    //LogTrack.e(e);
                }
            }
        }
    }
}

