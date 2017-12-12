package com.example.llcgs.android_kotlin.material.login.customtabs

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.example.llcgs.android_kotlin.material.webview.MaterialWebViewActivity
import com.gomejr.myf.core.kotlin.logD

/**
 * com.example.llcgs.android_kotlin.material.login.customtabs.WebViewFallback
 * @author liulongchao
 * @since 2017/12/12
 */
class WebViewFallback : CustomTabActivityHelper.CustomTabFallback {
    override fun openUri(activity: Activity, uri: Uri) {
        val intent = Intent(activity, MaterialWebViewActivity::class.java)
        intent.putExtra("EXTRA_URL", uri.toString())
        activity.startActivity(intent)
    }
}