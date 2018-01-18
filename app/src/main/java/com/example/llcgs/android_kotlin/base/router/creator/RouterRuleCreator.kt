package com.example.llcgs.android_kotlin.base.router.creator

import com.example.llcgs.android_kotlin.architecture_components.menu.MenuArchActivity
import com.example.llcgs.android_kotlin.base.router.action.HostAction
import com.example.llcgs.android_kotlin.base.router.action.UninstallAction
import com.example.llcgs.android_kotlin.design_pattern.list.DesignPatternMainActivity
import com.example.llcgs.android_kotlin.home.MainActivity
import com.example.llcgs.android_kotlin.list.MyListActivity
import com.example.llcgs.android_kotlin.material.douya.login.MaterialLoginActivity
import com.example.llcgs.android_kotlin.mvvm.login.MvvmActivity
import com.example.llcgs.android_kotlin.replugin.plugin.PluginActivity
import com.example.llcgs.android_kotlin.replugin.pluginlist.PluginListActivity
import com.lzh.nonview.router.executors.MainThreadExecutor
import com.lzh.nonview.router.module.ActionRouteRule
import com.lzh.nonview.router.module.ActivityRouteRule
import com.lzh.nonview.router.module.RouteCreator
import com.lzh.nonview.router.module.RouteRule
import java.util.*

class RouterRuleCreator : RouteCreator {
    override fun createActivityRouteRules(): Map<String, ActivityRouteRule> {
        val routes = HashMap<String, ActivityRouteRule>()
        routes.put("host://Architecture", ActivityRouteRule(MenuArchActivity::class.java))
        routes.put("host://Design_Pattern", ActivityRouteRule(DesignPatternMainActivity::class.java))
        routes.put("host://main", ActivityRouteRule(MainActivity::class.java))
        routes.put("host://list", ActivityRouteRule(MyListActivity::class.java))
        routes.put("host://Material_Design", ActivityRouteRule(MaterialLoginActivity::class.java))
        routes.put("host://mvvm", ActivityRouteRule(MvvmActivity::class.java))
        routes.put("host://plugin", ActivityRouteRule(PluginActivity::class.java))
        routes.put("host://listPlugin", ActivityRouteRule(PluginListActivity::class.java))
        return routes
    }

    override fun createActionRouteRules(): Map<String, ActionRouteRule> {
        val routes = HashMap<String, ActionRouteRule>()
        routes.put("host://action", ActionRouteRule(HostAction::class.java).setExecutorClass(MainThreadExecutor::class.java))
        routes.put("host://uninstall", ActionRouteRule(UninstallAction::class.java).addParam("plugin", RouteRule.STRING).setExecutorClass(MainThreadExecutor::class.java))
        return routes
    }
}
