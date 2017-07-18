package com.example.llcgs.android_kotlin.utils;

import android.app.Application;
import android.content.Context;

/**
 * 作者：Alex
 * 时间：2017/2/7 18:11
 * 简述：
 */
public class BaseUtil {
    private static Application application;
    private static boolean debug = true;
    private static String headTag = "MainActivity";

    public static void init(Application app) {
        application = app;
    }

    public static Context context() {
        return application;
    }

    public static boolean debug() {
        return debug;
    }

    public static void debug(boolean debug) {
        BaseUtil.debug = debug;
    }

    /**
     * 设置 消息 头
     *
     * @param headTag
     */
    public static void headTag(String headTag) {
        if (ObjUtil.isNotEmpty(headTag)) {
            BaseUtil.headTag = headTag;
        }
    }

    public static String headTag() {
        return headTag;
    }
}
