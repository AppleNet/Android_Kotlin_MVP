package com.example.llcgs.android_kotlin.net.webview.js

import android.net.Uri
import android.text.TextUtils
import com.example.llcgs.android_kotlin.net.webview.presenter.impl.WebViewJsBridgePresenter
import com.gomejr.myf.core.kotlin.logD
import org.json.JSONObject

/**
 * com.example.llcgs.android_kotlin.net.webview.js.JsCallJava
 * @author liulongchao
 * @since 2018/5/10
 */
object JsCallJava {

    const val JS_BRIDGE_PROTOCOL_SCHEMA = "gomejr"
    private var mClassName: String = ""
    private var mMethodName: String = ""
    private var mPort: String = ""
    private var mParams: JSONObject = JSONObject()

    fun call(message: String, defaultValue: String, presenter: WebViewJsBridgePresenter){
        parseMessage(message, presenter)
    }

    private fun parseMessage(message: String, presenter: WebViewJsBridgePresenter){
        if (!message.startsWith(JS_BRIDGE_PROTOCOL_SCHEMA))
            return
        val uri = Uri.parse(message)
        mClassName = uri.host
        val path = uri.path
        mMethodName = if (!TextUtils.isEmpty(path)){
            path.replace("/", "")
        }else {
            ""
        }
        mPort = uri.port.toString()
        if (uri.query != null){
            mParams = JSONObject(uri.query)
        }
        "mClassName: $mClassName, path: $path, mMethodName: $mMethodName, mParams: $mParams".logD()
        NativeMethodInjectHelper.findMethod(mClassName, mMethodName)?.invoke(presenter, mParams.toString())
    }
}