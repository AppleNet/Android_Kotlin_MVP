package com.example.llcgs.android_kotlin.base.router.action

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.lzh.compiler.parceler.annotation.Arg
import com.lzh.nonview.router.anno.RouterRule
import com.lzh.nonview.router.route.ActionSupport
import com.qihoo360.replugin.RePlugin

/**
 * com.example.llcgs.android_kotlin.base.action.UninstallAction
 * @author liulongchao
 * @since 2017/8/26
 */

@RouterRule("uninstall")
class UninstallAction: ActionSupport() {

    @Arg
    var plugin: String? = null

    override fun onRouteTrigger(context: Context?, bundle: Bundle?) {
        if (RePlugin.isPluginInstalled(plugin)) {
            RePlugin.uninstall(plugin)
            Toast.makeText(context, "卸载插件" + plugin + "完成，请重启app生效", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "当前无此插件：" + plugin, Toast.LENGTH_SHORT).show()
        }
    }
}