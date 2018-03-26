package com.example.llcgs.android_kotlin.base.app

import android.content.Context
import android.support.multidex.MultiDex
import com.alibaba.fastjson.JSON
import com.example.llcgs.android_kotlin.base.router.callback.HostEventCallbacks
import com.example.llcgs.android_kotlin.base.router.callback.KPluginCallback
import com.example.llcgs.android_kotlin.base.router.callback.KRouterCallBack
import com.example.llcgs.android_kotlin.base.router.creator.RouterRuleCreator
import com.example.llcgs.android_kotlin.base.router.interceptor.DefaultInterceptor
import com.example.llcgs.android_kotlin.base.router.packages.ComponentPackages
import com.example.llcgs.android_kotlin.base.router.remotefactory.HostRemoteFactory
import com.example.llcgs.android_kotlin.base.router.update.HostUpdateCombine
import com.example.llcgs.android_kotlin.base.router.update.JsonParser
import com.example.llcgs.android_kotlin.base.router.update.PluginChecker
import com.example.llcgs.android_kotlin.base.router.update.PluginStrategy
import com.example.llcgs.android_kotlin.base.router.verify.RePluginVerification
import com.example.llcgs.android_kotlin.utils.BaseUtil
import com.gomejr.myf.core.kotlin.logD
import com.lzh.nonview.router.RouterConfiguration
import com.lzh.nonview.router.anno.RouteConfig
import com.lzh.nonview.router.host.RouterHostService
import com.lzh.nonview.router.module.RouteCreator
import com.lzh.router.replugin.host.HostRouterConfiguration
import com.lzh.router.replugin.update.UpdateRePluginCallbacks
import com.qihoo360.replugin.RePlugin
import com.qihoo360.replugin.RePluginApplication
import com.qihoo360.replugin.RePluginCallbacks
import com.qihoo360.replugin.RePluginConfig
import org.lzh.framework.updatepluginlib.UpdateBuilder
import org.lzh.framework.updatepluginlib.UpdateConfig
import org.lzh.framework.updatepluginlib.model.CheckEntity
import org.lzh.framework.updatepluginlib.model.HttpMethod
import org.lzh.framework.updatepluginlib.model.Update


/**
 * com.example.llcgs.android_kotlin.base.app.KotlinApplication
 * @author liulongchao
 * @since 2017/7/26
 */

@RouteConfig(baseUrl = "host://", pack = "com.example.llcgs.android_kotlin.base.app")
class KotlinApplication: RePluginApplication() {

    override fun onCreate() {
        super.onCreate()
        BaseUtil.init(this)
        initRouter()
    }

    override fun attachBaseContext(base: Context?) {
        MultiDex.install(base)
        super.attachBaseContext(base)
        RePlugin.enableDebugger(base, true)
        //checkUpdata()
    }

    private fun initRouter(){
        // 启动远程路由前。加入安全验证器
        // 安全验证接口只存在于route-host依赖中。用于对启动的远程路由数据提供服务进行安全验证。避免被外部app随意连接，重置注册到远程服务中的路由表
        RouterHostService.setVerify(RePluginVerification())
        // 添加路由规则
        //loadRouteRulesIfExist()
        RouterConfiguration.get().addRouteCreator(RouterRuleCreator())
        // 添加全局拦截器
        RouterConfiguration.get().interceptor = DefaultInterceptor()

        // 当默认的动作路由启动方式不能满足你项目需要时。通过定制此接口来做替换
//        RouterConfiguration.get().setActionLauncher(CustomActionLauncher.class);
        // 当默认的页面路由启动方式不能满足你项目需要时，通过定制此接口来做替换
//        RouterConfiguration.get().setActivityLauncher(CustomActivityLauncher.class);

        // 初始化Config
        HostRouterConfiguration.init("com.example.llcgs.android_kotlin", this)
        // 远程数据提供者
        // 此接口用于对远程路由服务提供额外数据支持。
        // 当插件使用RouterConfiguration#startHostService启动远程路由服务并注册成功的时候。
        // 将会针对当前插件的每一条路由，生成对应的额外数据提供给其他插件使用。
        // 一般是用于定制插件间页面跳转使用
        RouterConfiguration.get().remoteFactory = HostRemoteFactory()
        HostRouterConfiguration.get().setCallback(KPluginCallback())
        HostRouterConfiguration.get().setRouteCallback(KRouterCallBack())
    }

    /**
     * 通过反射加载通过Router框架生成的路由映射表。此处会加载各个组件中通过运行时注解生成的路由表
     */
    private fun loadRouteRulesIfExist() {
        val packs = ComponentPackages.Packages
        val clzNameRouteRules = ".RouterRuleCreator"
        for (pack in packs) {
            try {
                pack.logD()
                val creator = Class.forName(pack + clzNameRouteRules)
                val instance = creator.newInstance() as RouteCreator
                // 添加路由规则
                RouterConfiguration.get().addRouteCreator(instance)
            } catch (ignore: Exception) {
                "反射失败...".logD()
            }
        }
    }

    override fun createConfig(): RePluginConfig {
        val c = RePluginConfig()
        // 允许“插件使用宿主类”。默认为“关闭”
        c.isUseHostClassIfNotFound = true
        // RePlugin默认会对安装的外置插件进行签名校验，这里先关掉，避免调试时出现签名错误
        c.verifySign = false
        // 针对“安装失败”等情况来做进一步的事件处理
        c.eventCallbacks = HostEventCallbacks(this)
        // 若宿主为Release，则此处应加上您认为"合法"的插件的签名，例如，可以写上"宿主"自己的。
        return c
    }

    override fun createCallbacks(): RePluginCallbacks {
        // TODO 只在要启动的activity不存在的适合 触发更新逻辑，不太适合插件的更新
        return UpdateRePluginCallbacks(this,
                // 设置UpdateConfig。用于进行远程plugin更新。
                UpdateConfig.createConfig()
                        // 检测插件是否有更新
                        .updateChecker(PluginChecker())
                        // 如果有 解析返回的数据 目的是为了获取更新的url下载链接
                        .jsonParser(JsonParser())
                        // 展示下载UI
                        .strategy(PluginStrategy()),
                // 设置远程插件更新接口api组装。
                HostUpdateCombine())
    }

    fun checkUpdata(){
        // 建议在Application中进行配置。
        // UpdateConfig为全局配置。当在其他页面中。使用UpdateBuilder进行检查更新时。
        // 对于没传的参数，会默认使用UpdateConfig中的全局配置
        UpdateConfig.getConfig()
                // imageUrl 与 checkEntity方法可任选一种填写，且至少必填一种。
                // 数据更新接口数据，此时默认为使用GET请求
                //.imageUrl()
                // 类似url方法。CheckEntity方法可填写url,params,method。可在此设置为使用post请求
                .checkEntity(CheckEntity().setUrl(/*此处放置url*/"http://10.143.117.45:8081/sm-api/").setMethod(HttpMethod.POST).setParams(mapOf(
                        "version" to "3.0.13",
                        "phoneType" to "1",
                        "appType" to "1"
                )))
                // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
                .jsonParser { httpResponse ->
                    httpResponse?.logD()
                    JSON.parseObject(httpResponse, Update::class.java)
                }
        UpdateBuilder.create().check()
    }

}