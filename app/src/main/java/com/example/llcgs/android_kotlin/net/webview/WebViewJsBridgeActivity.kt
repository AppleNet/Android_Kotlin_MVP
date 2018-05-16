package com.example.llcgs.android_kotlin.net.webview

import android.annotation.SuppressLint
import android.webkit.JsPromptResult
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.net.base.BaseNetWorkActivity
import com.example.llcgs.android_kotlin.net.webview.js.JsBridge
import com.example.llcgs.android_kotlin.net.webview.js.JsCallJava
import com.example.llcgs.android_kotlin.net.webview.presenter.IWebViewJsBridgePresenter
import com.example.llcgs.android_kotlin.net.webview.presenter.impl.WebViewJsBridgePresenter
import com.example.llcgs.android_kotlin.net.webview.view.WebViewJsBridgeView
import com.example.llcgs.android_kotlin.utils.log.logD
import kotlinx.android.synthetic.main.activity_webview_jsbridge.*

/**
 * com.example.llcgs.android_kotlin.net.webview.WebViewJsBridgeActivity
 * @author liulongchao
 * @since 2018/5/9
 */
class WebViewJsBridgeActivity: BaseNetWorkActivity<IWebViewJsBridgePresenter>(), WebViewJsBridgeView {

    override fun createPresenter(): IWebViewJsBridgePresenter= WebViewJsBridgePresenter(this)

    override fun getLayoutId()= R.layout.activity_webview_jsbridge

    @SuppressLint("SetJavaScriptEnabled")
    override fun initViews() {
        // 将WebViewJsBridgePresenter的所有方法注入map中 供js调用
        JsBridge.clazz(WebViewJsBridgePresenter::class.java).inject()
        val s = webView.settings
        s.builtInZoomControls = true
        /**
         * 指定WebView的页面布局显示形式，调用该方法会引起页面重绘。默认LayoutAlgorithm#NARROW_COLUMNS
         */
        s.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        /**
         * 是否支持ViewPort的meta tag属性，如果页面有ViewPort meta tag 指定的宽度，则使用meta tag指定的值，否则默认使用宽屏的视图窗口
         */
        s.useWideViewPort = true
        /**
         * 是否启动概述模式浏览界面，当页面宽度超过WebView显示宽度时，缩小页面适应WebView。默认false
         */
        s.loadWithOverviewMode = true
        /**
         * 是否保存密码，默认false
         */
        s.savePassword = true
        /**
         * 是否保存表单数据，默认false
         */
        s.saveFormData = true
        s.javaScriptEnabled = true
        /**
         * 是否允许定位，默认true。注意：为了保证定位可以使用，要保证以下几点：
         * Application 需要有android.Manifest.permission#ACCESS_COARSE_LOCATION的权限
         * Application 需要实现WebChromeClient#onGeolocationPermissionsShowPrompt的回调，
         * 接收Js定位请求访问地理位置的通知
         */
        s.setGeolocationEnabled(true)
        /**
         * 是否存储页面DOM结构，默认false。
         */
        s.domStorageEnabled = true
        s.cacheMode = WebSettings.LOAD_NO_CACHE//不缓存

        webView.webChromeClient = object : WebChromeClient() {

            override fun onJsPrompt(view: WebView, url: String, message: String, defaultValue: String, result: JsPromptResult): Boolean {
                message.logD()
                JsCallJava.call(message, defaultValue, mPresenter as WebViewJsBridgePresenter)
                return true
            }
        }

        webView.loadUrl("file:///android_asset/index.html")
    }

    override fun onJsSuccess(string: String) {
        "url: $string".logD()
        webView.loadUrl(string)
    }


}