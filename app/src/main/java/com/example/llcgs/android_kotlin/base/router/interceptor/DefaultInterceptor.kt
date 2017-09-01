package com.example.llcgs.android_kotlin.base.router.interceptor

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.example.llcgs.android_kotlin.base.spreferences.SharedPreferencesUtils
import com.lzh.nonview.router.extras.RouteBundleExtras
import com.lzh.nonview.router.interceptors.RouteInterceptor

/**
 * com.example.llcgs.android_kotlin.base.router.interceptor.DefaultInterceptor
 * @author liulongchao
 * @since 2017/8/29
 */


class DefaultInterceptor: RouteInterceptor {

    override fun intercept(uri: Uri?, extras: RouteBundleExtras?, context: Context?): Boolean {
        return !DataManager.INSTANCE.isLogin()
    }

    override fun onIntercepted(uri: Uri?, extras: RouteBundleExtras?, context: Context?) {
        Toast.makeText(context, "未登录.请先登录", Toast.LENGTH_SHORT).show()
        SharedPreferencesUtils.setLoginName("Kobe")
        DataManager.INSTANCE.setLogin(true)
    }
}