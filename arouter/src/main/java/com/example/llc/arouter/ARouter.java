package com.example.llc.arouter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import dalvik.system.DexFile;

/**
 * com.example.llc.arouter.ARouter
 *
 * @author liulongchao
 * @since 2020-07-10
 * <p>
 * 路由表，装载所有的 Activity
 */
public class ARouter {

    // 装载了所有的 Activity 点类对象，路由表
    private HashMap<String, Class<? extends Activity>> mClassHashMap;
    private Context mContext;

    private ARouter() {
        mClassHashMap = new HashMap<>();
    }

    private static class ARouterHolder {
        private static final ARouter A_ROUTER = new ARouter();
    }

    public void init(Context context) {
        this.mContext = context;
        //
        List<String> classNames = getClassName("com.example.llc.arouter");
        for (String className : classNames) {
            try {
                Class<?> utilClass = Class.forName(className);
                if (IRouter.class.isAssignableFrom(utilClass)) {
                    IRouter iRouter = (IRouter) utilClass.newInstance();
                    iRouter.putActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ARouter getInstance() {
        return ARouterHolder.A_ROUTER;
    }

    /**
     * 将类对象添加到路由表中
     */
    public void addActivity(String key, Class<? extends Activity> clazz) {
        if (!TextUtils.isEmpty(key) && clazz != null && !mClassHashMap.containsKey(key)) {
            mClassHashMap.put(key, clazz);
        }
    }

    /**
     * 跳转窗体方法
     *
     * @param key    key
     * @param bundle bundle
     */
    public void startActivity(String key, Bundle bundle) {
        Class<? extends Activity> aClass = mClassHashMap.get(key);
        if (aClass != null) {
            Intent intent = new Intent(mContext, aClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            mContext.startActivity(intent);
        }
    }

    /**
     *  通过包名获取这个包下面的所有的类名
     *
     * */
    private List<String> getClassName(String packageName) {
        List<String> classList = new ArrayList<>();
        try {
            DexFile df = new DexFile(mContext.getPackageCodePath());
            Enumeration<String> entries = df.entries();
            while (entries.hasMoreElements()) {
                String className = entries.nextElement();
                if (className.contains(packageName)) {
                    classList.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classList;
    }

}
