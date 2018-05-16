package com.example.llcgs.android_kotlin.base.router.callback

import android.net.Uri
import com.example.llcgs.android_kotlin.utils.log.logD
import com.lzh.router.replugin.core.IPluginCallback

/**
 * com.example.llcgs.android_kotlin.base.router.callback.KPluginCallback
 * @author liulongchao
 * @since 2017/8/26
 */


class KPluginCallback: IPluginCallback {
    override fun notFound(uri: Uri?, alias: String?) {
        "此uri所代表的路由未找到时且此uri所对应的插件也启动了的情况下".logD()
    }

    override fun onInvalidUri(uri: Uri?) {
        "uri所代表的路由为无效时".logD()
    }

    override fun onResume(uri: Uri?) {
        "此uri对应的插件被加载成功。且成功恢复时。回调通知到此".logD()
    }
}