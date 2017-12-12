package com.example.llcgs.android_kotlin.material.webview.view

import android.content.Intent
import android.webkit.WebView
import com.example.llcgs.android_kotlin.material.base.BaseMaterialView

/**
 * com.example.llcgs.android_kotlin.material.webview.view.MaterialWebViewView
 * @author liulongchao
 * @since 2017/12/12
 */
interface MaterialWebViewView : BaseMaterialView {

    fun onSetupWebViewSuccess(webView: WebView)

    fun onUpdateToolbarTitleAndSubtitle(hostTitle: String, title: String, url: String)

    fun onShare(intent: Intent)

    fun onBrowser(intent: Intent)
}