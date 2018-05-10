package com.example.llcgs.android_kotlin.net.webview

import android.webkit.JsPromptResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.net.base.BaseNetWorkActivity
import com.example.llcgs.android_kotlin.net.webview.js.JsBridge
import com.example.llcgs.android_kotlin.net.webview.js.JsCallJava
import com.example.llcgs.android_kotlin.net.webview.presenter.IWebViewJsBridgePresenter
import com.example.llcgs.android_kotlin.net.webview.presenter.impl.WebViewJsBridgePresenter
import com.example.llcgs.android_kotlin.net.webview.view.WebViewJsBridgeView
import kotlinx.android.synthetic.main.activity_webview_jsbridge.*

/**
 * com.example.llcgs.android_kotlin.net.webview.WebViewJsBridgeActivity
 * @author liulongchao
 * @since 2018/5/9
 */
class WebViewJsBridgeActivity: BaseNetWorkActivity<IWebViewJsBridgePresenter>(), WebViewJsBridgeView {

    override fun createPresenter(): IWebViewJsBridgePresenter= WebViewJsBridgePresenter(this)

    override fun getLayoutId()= R.layout.activity_webview_jsbridge

    override fun initViews() {
        // 将WebViewJsBridgePresenter的所有方法注入map中 供js调用
        JsBridge.clazz(WebViewJsBridgePresenter::class.java).inject()

        webView.webChromeClient = object : WebChromeClient() {

            override fun onJsPrompt(view: WebView, url: String, message: String, defaultValue: String, result: JsPromptResult): Boolean {
                JsCallJava.call(message, defaultValue)
                return true
            }

        }
    }


}