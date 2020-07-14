package com.example.llc.james;

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import com.example.llc.kotlin.basic.BaseModuleActivity

/**
 * com.example.llc.james.BaseModuleJamesActivity
 * @author liulongchao
 * @since 2017/7/26
 *
 * 处理AppCompatActivity
 */
abstract class BaseModuleJamesActivity: BaseModuleActivity() {

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
}
