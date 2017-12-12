package com.example.llcgs.android_kotlin.material.webview.presenter

import android.content.Context
import android.webkit.WebView
import com.example.llcgs.android_kotlin.material.base.BaseMaterialPresenter

/**
 * com.example.llcgs.android_kotlin.material.webview.presenter.IMaterialWebViewPresenter
 * @author liulongchao
 * @since 2017/12/12
 */
interface IMaterialWebViewPresenter : BaseMaterialPresenter {

    fun setupWebView(webView: WebView)

    fun loadUrl(webView: WebView, url: String)

    fun updateToolbarTitleAndSubtitle(url: String, title: String)

    fun share(url: String)

    fun browser(url: String)

    fun copy(context: Context, title:String, url: String)
}