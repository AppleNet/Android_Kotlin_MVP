package com.example.llcgs.android_kotlin.material.douya.webview.client

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * com.example.llcgs.android_kotlin.material.webview.client.ViewClient
 * @author liulongchao
 * @since 2017/12/12
 */
class ViewClient: WebViewClient() {

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        onPageStarted?.let {
            it(view, url, favicon)
        }
    }

    private var onPageStarted: ((view: WebView, url: String, favicon: Bitmap?)->Unit)? = null

    fun setOnPageStated(listener: ((view: WebView, url: String, favicon: Bitmap?)->Unit)){
        onPageStarted = listener
    }
}