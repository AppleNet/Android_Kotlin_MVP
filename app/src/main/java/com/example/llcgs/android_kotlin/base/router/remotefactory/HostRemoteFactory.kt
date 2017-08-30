package com.example.llcgs.android_kotlin.base.router.remotefactory

import android.content.Context
import android.os.Bundle
import com.lzh.nonview.router.launcher.Launcher
import com.lzh.nonview.router.module.RouteRule
import com.lzh.nonview.router.protocol.IRemoteFactory

/**
 * com.example.llcgs.android_kotlin.base.router.remotefactory.HostRemoteFactory
 * @author liulongchao
 * @since 2017/8/30
 */


class HostRemoteFactory: IRemoteFactory {
    // 所有宿主、插件的路由表配置都会被注册到这个远程服务中。
    // 达到让宿主、插件共享路由表信息的目的
    override fun createRemote(application: Context?, rule: RouteRule<out RouteRule<*, *>, out Launcher<*>>?): Bundle {
        // 提供额外的数据支持，类似于全局数据
        val bundle = Bundle()
        bundle.putString("publicName", "Kobe")
        bundle.putString("publicPwd", "38")
        return bundle
    }
}