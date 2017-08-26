package com.example.llcgs.android_kotlin.base.router.callback

import android.net.Uri
import com.gomejr.myf.core.kotlin.logD
import com.lzh.nonview.router.exception.NotFoundException
import com.lzh.nonview.router.launcher.Launcher
import com.lzh.nonview.router.module.RouteRule
import com.lzh.nonview.router.route.RouteCallback

/**
 * com.example.llcgs.android_kotlin.base.router.callback.KRouterCallBack
 * @author liulongchao
 * @since 2017/8/26
 */


class KRouterCallBack: RouteCallback {
    override fun notFound(uri: Uri?, e: NotFoundException?) {
        "uri: ${uri} notFound".logD()
    }

    override fun onOpenSuccess(uri: Uri?, rule: RouteRule<out RouteRule<*, *>, out Launcher<*>>?) {
        "uri: ${uri} onOpenSuccess".logD()
    }

    override fun onOpenFailed(uri: Uri?, e: Throwable?) {
        "uri: ${uri} onOpenFailed".logD()
    }
}