package com.example.llcgs.android_kotlin.replugin.pluginlist;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.llcgs.android_kotlin.R;
import com.qihoo360.replugin.RePlugin;

/**
 * PluginListFragmentActivity
 *
 * @author liulongchao
 * @since 2017/8/30
 */


public class PluginListFragmentActivity extends FragmentActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注册相关Fragment的类
        //注册一个全局Hook用于拦截系统对XX类的寻找定向到Demo1中的XX类主要是用于在xml中可以直接使用插件中的类
        RePlugin.registerHookingClass("com.qihoo360.replugin.sample.demo1.fragment.DemoFragment",
                RePlugin.createComponentName("demo1", "com.qihoo360.replugin.sample.demo1.fragment.DemoFragment"), null);
        setContentView(R.layout.activity_plugin_fragment);

        // 代码使用插件Fragment，需要本地的Activity来attach Fragment

        //获取插件的ClassLoader
        ClassLoader d1ClassLoader = RePlugin.fetchClassLoader("demo1");
        try {

            Fragment fragment = d1ClassLoader
                    .loadClass("com.qihoo360.replugin.sample.demo1.fragment.DemoCodeFragment")
                    .asSubclass(Fragment.class)
                    .newInstance();//使用插件的Classloader获取指定Fragment实例

            //添加Fragment到UI
            getSupportFragmentManager().beginTransaction().add(R.id.container2, fragment).commit();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
