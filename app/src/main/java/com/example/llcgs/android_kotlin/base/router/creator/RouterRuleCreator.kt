package com.example.llcgs.android_kotlin.base.router.creator

import com.example.llcgs.android_kotlin.algorithms.AlgorithmsListActivity
import com.example.llcgs.android_kotlin.animator.AnimatorActivity
import com.example.llcgs.android_kotlin.architecture_components.menu.MenuArchActivity
import com.example.llcgs.android_kotlin.base.router.action.HostAction
import com.example.llcgs.android_kotlin.base.router.action.UninstallAction
import com.example.llcgs.android_kotlin.constraintlayout.ConstraintLayoutActivity
import com.example.llcgs.android_kotlin.customview.CustomViewActivity
import com.example.llcgs.android_kotlin.design_pattern.list.DesignPatternMainActivity
import com.example.llcgs.android_kotlin.home.MainActivity
import com.example.llcgs.android_kotlin.list.MyListActivity
import com.example.llcgs.android_kotlin.material.animation.touch_feedback.TouchFeedbackActivity
import com.example.llcgs.android_kotlin.material.douya.login.MaterialLoginActivity
import com.example.llcgs.android_kotlin.material.mediaplayer.player.MusicPlayerActivity
import com.example.llcgs.android_kotlin.modules.activity.ActivityListActivity
import com.example.llcgs.android_kotlin.modules.service.ServiceListActivity
import com.example.llcgs.android_kotlin.mvvm.login.MvvmActivity
import com.example.llcgs.android_kotlin.net.NetWorkListActivity
import com.example.llcgs.android_kotlin.replugin.plugin.PluginActivity
import com.example.llcgs.android_kotlin.replugin.pluginlist.PluginListActivity
import com.example.llcgs.android_kotlin.tinker.TinkerActivity
import com.example.llcgs.android_kotlin.ui.UIListActivity
import com.example.llcgs.android_kotlin.view_dispatcher.ViewDispatcherActivity
import com.example.llcgs.android_kotlin.window.WindowActivity
import com.lzh.nonview.router.executors.MainThreadExecutor
import com.lzh.nonview.router.module.ActionRouteRule
import com.lzh.nonview.router.module.ActivityRouteRule
import com.lzh.nonview.router.module.RouteCreator
import com.lzh.nonview.router.module.RouteRule
import java.util.*

class RouterRuleCreator : RouteCreator {
    override fun createActivityRouteRules(): Map<String, ActivityRouteRule> {
        val routes = HashMap<String, ActivityRouteRule>()
        routes["host://ViewDispatcher"] = ActivityRouteRule(ViewDispatcherActivity::class.java)
        routes["host://ViewPager_Activity_TabLayout"] = ActivityRouteRule(UIListActivity::class.java)
        routes["host://main"] = ActivityRouteRule(MainActivity::class.java)
        routes["host://Tinker"] = ActivityRouteRule(TinkerActivity::class.java)
        routes["host://NetWork"] = ActivityRouteRule(NetWorkListActivity::class.java)
        routes["host://Algorithms"] = ActivityRouteRule(AlgorithmsListActivity::class.java)
        routes["host://Animator"] = ActivityRouteRule(AnimatorActivity::class.java)
        routes["host://Design_Pattern"] = ActivityRouteRule(DesignPatternMainActivity::class.java)
        routes["host://Window"] = ActivityRouteRule(WindowActivity::class.java)
        routes["host://list"] = ActivityRouteRule(MyListActivity::class.java)
        routes["host://CustomView"] = ActivityRouteRule(CustomViewActivity::class.java)
        routes["host://Architecture"] = ActivityRouteRule(MenuArchActivity::class.java)
        routes["host://Activity"] = ActivityRouteRule(ActivityListActivity::class.java)
        routes["host://Service"] = ActivityRouteRule(ServiceListActivity::class.java)
        routes["host://plugin"] = ActivityRouteRule(PluginActivity::class.java)
        routes["host://listPlugin"] = ActivityRouteRule(PluginListActivity::class.java)
        routes["host://Material_MediaPlayer"] = ActivityRouteRule(MusicPlayerActivity::class.java)
        routes["host://Material_Animation"] = ActivityRouteRule(TouchFeedbackActivity::class.java)
        routes["host://Material_Design"] = ActivityRouteRule(MaterialLoginActivity::class.java)
        routes["host://mvvm"] = ActivityRouteRule(MvvmActivity::class.java)
        routes["host://ConstraintLayout"] = ActivityRouteRule(ConstraintLayoutActivity::class.java)
        return routes
    }

    override fun createActionRouteRules(): Map<String, ActionRouteRule> {
        val routes = HashMap<String, ActionRouteRule>()
        routes["host://uninstall"] = ActionRouteRule(UninstallAction::class.java).addParam("plugin", RouteRule.STRING).setExecutorClass(MainThreadExecutor::class.java)
        routes["host://action"] = ActionRouteRule(HostAction::class.java).setExecutorClass(MainThreadExecutor::class.java)
        return routes
    }
}
