package com.example.llcgs.android_kotlin.base.app

import android.content.Context
import com.alibaba.fastjson.JSON
import com.example.llcgs.android_kotlin.base.router.callback.HostEventCallbacks
import com.example.llcgs.android_kotlin.base.router.callback.KPluginCallback
import com.example.llcgs.android_kotlin.base.router.callback.KRouterCallBack
import com.example.llcgs.android_kotlin.base.router.creator.RouterRuleCreator
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
import org.lzh.framework.updatepluginlib.model.UpdateParser


/**
 * com.example.llcgs.android_kotlin.base.app.KotlinApplication
 * @author liulongchao
 * @since 2017/7/26
 */

@RouteConfig(baseUrl = "host://")
class KotlinApplication: RePluginApplication() {

    override fun onCreate() {
        super.onCreate()
        BaseUtil.init(this)
        initRouter()

    }

    private fun initRouter(){
        // 启动远程路由前。加入安全验证器
        RouterHostService.setVerify(RePluginVerification())
        // 添加路由规则
        RouterConfiguration.get().addRouteCreator(RouterRuleCreator())
        // 初始化Config
        HostRouterConfiguration.init("com.example.llcgs.android_kotlin", this)
        HostRouterConfiguration.get().setCallback(KPluginCallback())
        HostRouterConfiguration.get().setRouteCallback(KRouterCallBack())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        RePlugin.enableDebugger(base, true)
        checkUpdata()
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
                // url 与 checkEntity方法可任选一种填写，且至少必填一种。
                // 数据更新接口数据，此时默认为使用GET请求
                //.url()
                // 类似url方法。CheckEntity方法可填写url,params,method。可在此设置为使用post请求
                .checkEntity(CheckEntity().setUrl(/*此处放置url*/"").setMethod(HttpMethod.POST).setParams(mapOf(
                        "version" to "3.0.13",
                        "phoneType" to "1",
                        "appType" to "1"
                )))
                // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
                .jsonParser(object : UpdateParser{
                    override fun parse(httpResponse: String?): Update {
                        httpResponse?.logD()
                        return JSON.parseObject(httpResponse, Update::class.java)
                    }
                })
        UpdateBuilder.create().check()
    }

}