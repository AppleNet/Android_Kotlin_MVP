package com.example.llc.james;

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ContextThemeWrapper
import android.view.LayoutInflater

/**
 * com.example.llc.james.JamesBaseActivity
 * @author liulongchao
 * @since 2017/7/26
 *
 * 处理AppCompatActivity
 */
abstract class JamesBaseActivity: AppCompatActivity() {

    private var mContextThemeWrapper: ContextThemeWrapper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val resources = PluginLoad.getResources(application)
        if(resources != null) {
            mContextThemeWrapper = ContextThemeWrapper(baseContext, 0)
            // 替换插件的
            val clazz = mContextThemeWrapper?.javaClass
            val mResources = clazz?.getDeclaredField("mResources")
            mResources?.isAccessible = true
            mResources?.set(mContextThemeWrapper, resources)
        }

        val view  = LayoutInflater.from(mContextThemeWrapper).inflate(getLayoutId(), null)
        setContentView(view)
    }

    abstract fun getLayoutId(): Int
}
