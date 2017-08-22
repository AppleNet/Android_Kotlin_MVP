package com.example.llcgs.android_kotlin.base.app

import android.content.Context
import android.content.Intent
import android.os.Environment
import com.example.llcgs.android_kotlin.utils.BaseUtil
import com.gomejr.myf.core.kotlin.logD
import com.qihoo360.replugin.*
import java.io.File


/**
 * com.example.llcgs.android_kotlin.base.app.KotlinApplication
 * @author liulongchao
 * @since 2017/7/26
 */


class KotlinApplication: RePluginApplication() {

    override fun onCreate() {
        super.onCreate()
        BaseUtil.init(this)
        //initPlugin()
    }

    fun initPlugin(){
        val pi = RePlugin.install(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "GomeXG" + File.separator + "app-debug.apk")
        "pi != null result:  ${pi != null}".logD()
        if (pi != null){
            val preload = RePlugin.preload(pi)
            "load result:  ${preload}".logD()
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        RePlugin.enableDebugger(base, true)
    }

    override fun createConfig(): RePluginConfig {
        val c = RePluginConfig()
        // 允许“插件使用宿主类”。默认为“关闭”
        c.setUseHostClassIfNotFound(true)
        // RePlugin默认会对安装的外置插件进行签名校验，这里先关掉，避免调试时出现签名错误
        c.setVerifySign(false)
        // 针对“安装失败”等情况来做进一步的事件处理
        c.setEventCallbacks(HostEventCallbacks(this))
        // 若宿主为Release，则此处应加上您认为"合法"的插件的签名，例如，可以写上"宿主"自己的。
        return c
    }

    override fun createCallbacks(): RePluginCallbacks {
        return HostCallbacks(this)
    }

    class HostCallbacks(context: Context?) : RePluginCallbacks(context) {

        override fun onPluginNotExistsForActivity(context: Context?, plugin: String?, intent: Intent?, process: Int): Boolean {
            // FIXME 当插件"没有安装"时触发此逻辑，可打开您的"下载对话框"并开始下载。
            // FIXME 其中"intent"需传递到"对话框"内，这样可在下载完成后，打开这个插件的Activity

            return super.onPluginNotExistsForActivity(context, plugin, intent, process)
        }
    }

    class HostEventCallbacks(context: Context?) : RePluginEventCallbacks(context) {

        override fun onInstallPluginFailed(path: String?, code: InstallResult?) {
            // FIXME 当插件安装失败时触发此逻辑。您可以在此处做“打点统计”，也可以针对安装失败情况做“特殊处理”
            // 大部分可以通过RePlugin.install的返回值来判断是否成功
            "onInstallPluginFailed: Failed! path = ${path}; r = ${code}".logD()
            super.onInstallPluginFailed(path, code)
        }

        override fun onStartActivityCompleted(plugin: String?, activity: String?, result: Boolean) {
            // FIXME 当打开Activity成功时触发此逻辑，可在这里做一些APM、打点统计等相关工作
            super.onStartActivityCompleted(plugin, activity, result)
        }
    }

}