package com.example.llcgs.android_kotlin.material.douya.login.customtabs

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.example.llcgs.android_kotlin.material.douya.webview.MaterialWebViewActivity
import com.gomejr.myf.core.kotlin.logD

/**
 * com.example.llcgs.android_kotlin.material.login.customtabs.WebViewFallback
 * @author liulongchao
 * @since 2017/12/12
 */
class WebViewFallback : com.example.llcgs.android_kotlin.material.douya.login.customtabs.CustomTabActivityHelper.CustomTabFallback {
    override fun openUri(activity: Activity, uri: Uri) {
        val intent = Intent(activity, MaterialWebViewActivity::class.java)
        intent.putExtra("EXTRA_URL", uri.toString())
        activity.startActivity(intent)
    }
}