package com.example.llcgs.android_kotlin.plugin.interceptor

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.example.llcgs.android_kotlin.home.bean.User
import com.gomejr.myf.core.kotlin.logD
import com.lzh.nonview.router.extras.RouteBundleExtras
import com.lzh.nonview.router.interceptors.RouteInterceptor

/**
 * com.example.llcgs.android_kotlin.plugin.interceptor.PluginInterceptor
 * @author liulongchao
 * @since 2017/8/29
 */


class PluginInterceptor(var id:String, var user:User):RouteInterceptor {

    override fun intercept(uri: Uri?, extras: RouteBundleExtras?, context: Context?): Boolean {
        uri?.logD()
        id?.logD()
        user?.name?.logD()
        user?.pwd?.logD()
        return id == "001" && user.name == "McGrady" && user.pwd == "1234567"
    }

    override fun onIntercepted(uri: Uri?, extras: RouteBundleExtras?, context: Context?) {
        Toast.makeText(context, "用户名或密码错误，请重新登录", Toast.LENGTH_SHORT).show()
        //Router.create("host://main").open(context)
    }
}