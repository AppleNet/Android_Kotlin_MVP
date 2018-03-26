package com.example.llc.base


import android.support.multidex.MultiDexApplication
import com.lzh.nonview.router.RouterConfiguration
import com.lzh.nonview.router.anno.RouteConfig

/**
 * com.example.llc.base.JamesApplication
 * @author liulongchao
 * @since 2018/3/23
 */
@RouteConfig(baseUrl = "plugin://", pack = "com.example.llc.base")
class JamesApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        RouterConfiguration.get().addRouteCreator(RouterRuleCreator())
    }

}