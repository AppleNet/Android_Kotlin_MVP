package com.example.llc.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.lzh.nonview.router.RouterConfiguration
import com.lzh.nonview.router.anno.RouteConfig
import com.lzh.router.RouterRuleCreator

/**
 * com.example.llc.base.JamesApplication
 * @author liulongchao
 * @since 2018/3/23
 */
@RouteConfig(baseUrl = "james://")
class JamesApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        RouterConfiguration.get().addRouteCreator(RouterRuleCreator())
    }

}