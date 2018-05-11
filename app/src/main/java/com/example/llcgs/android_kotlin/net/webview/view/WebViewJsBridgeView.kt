package com.example.llcgs.android_kotlin.net.webview.view

import com.example.llcgs.android_kotlin.net.base.BaseNetWorkView

/**
 * com.example.llcgs.android_kotlin.net.webview.view.WebViewJsBridgeView
 * @author liulongchao
 * @since 2018/5/9
 */
interface WebViewJsBridgeView: BaseNetWorkView {

    fun onJsSuccess(string: String)

}