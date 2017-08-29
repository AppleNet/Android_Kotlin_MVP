package com.example.llcgs.android_kotlin.base.router.callback

import android.content.Context
import com.gomejr.myf.core.kotlin.logD
import com.qihoo360.replugin.RePluginEventCallbacks

/**
 * com.example.llcgs.android_kotlin.base.router.callback.HostEventCallbacks
 * @author liulongchao
 * @since 2017/8/28
 */


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