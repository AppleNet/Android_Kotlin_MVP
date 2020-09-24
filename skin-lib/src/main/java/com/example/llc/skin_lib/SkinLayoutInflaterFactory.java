package com.example.llc.skin_lib;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.example.llc.skin_lib.utils.SkinThemeUtils;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 *  com.example.llc.skin_lib.SkinLayoutInflaterFactory
 *
 * @author liulongchao
 * @since 2020-09-23
 *
 *  用来接管系统 view 的生成过程
 *
 * */
public class SkinLayoutInflaterFactory implements LayoutInflater.Factory2, Observer {


    private static final String[] mClassPrefixList = {
            "android.widget.",
            "android.webkit.",
            "android.app.",
            "android.view.",
    };

    // 记录对应 view 的构造函数
    private static final Class<?>[] mConstructorSignature = new Class[] {
            Context.class, AttributeSet.class
    };

    private static final HashMap<String, Constructor<? extends View>> sConstructorMap = new HashMap<>();

    // 当选择新皮肤后需要替换 View 与之对应的属性
    // 页面属性管理器
    private SkinAttribute mSkinAttribute;
    // 用于获取窗口的状态框的信息
    private Activity mActivity;

    SkinLayoutInflaterFactory(Activity activity) {
        this.mActivity = activity;
        this.mSkinAttribute = new SkinAttribute();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        // 换肤就是在需要的时候替换 View 的属性(src background 等)
        // 所以这里创建 view 从而修改 View 属性
        View view = createSDKView(name, context, attrs);
        if (view == null) {
            view = createView(name, context, attrs);
        }
        if (null != view) {
            // 加载属性
            mSkinAttribute.look(view, attrs);
        }
        return view;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = createSDKView(name, context, attrs);
        if (view == null) {
            view = createView(name, context, attrs);
        }
        if (null != view) {
            mSkinAttribute.look(view, attrs);
        }
        return view;
    }

    private Constructor<? extends View> findConstructor(Context context, String name) {
        Constructor<? extends View> constructor = sConstructorMap.get(name);
        if (constructor == null) {
            try {
                Class<? extends View> clazz = context.getClassLoader().loadClass(name).asSubclass(View.class);
                constructor = clazz.getConstructor(mConstructorSignature);
                constructor.setAccessible(true);
                sConstructorMap.put(name, constructor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return constructor;
    }

    private View createView(String name, Context context, AttributeSet attrs) {
        Constructor<? extends View> constructor = findConstructor(context, name);
        try {
            return constructor.newInstance(context, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private View createSDKView(String name, Context context, AttributeSet attrs) {
        // 如果包含 . 则不是 SDK 中的 View 可能是自定义 View 包括 support 库中的 View
        if (-1 == name.indexOf('.')) {
            return null;
        }

        for (String s : mClassPrefixList) {
            View view = createView(s + name, context, attrs);
            if (view != null) {
                return view;
            }
        }
        return null;
    }

    /**
     *  Activity(Observable)发出通知，这里就会执行
     *
     *  执行换肤操作
     * */
    @Override
    public void update(Observable o, Object arg) {
        SkinThemeUtils.updateStatusBarColor(mActivity);
        mSkinAttribute.applySkin();
    }
}
