package com.example.llcgs.android_kotlin.net.webview.js

import android.net.Uri
import android.text.TextUtils
import com.gomejr.myf.core.kotlin.logD
import org.json.JSONObject

/**
 * com.example.llcgs.android_kotlin.net.webview.js.JsCallJava
 * @author liulongchao
 * @since 2018/5/10
 */
object JsCallJava {

    val JS_BRIDGE_PROTOCOL_SCHEMA = "gomejr"
    var mClassName: String = ""
    var mMethodName: String = ""
    var mPort: String = ""
    var mParams: JSONObject = JSONObject()

    fun call(message: String, defaultValue: String){
        parseMessage(message)
    }

    private fun parseMessage(message: String){
        if (!message.startsWith(JS_BRIDGE_PROTOCOL_SCHEMA))
            return
        val uri = Uri.parse(message)
        mClassName = uri.host
        val path = uri.path
        "mClassName: $mClassName, path: $path".logD()
        mMethodName = if (!TextUtils.isEmpty(path)){
            path.replace("/", "")
        }else {
            ""
        }
        mPort = uri.port.toString()
        if (uri.query != null){
            mParams = JSONObject(uri.query)
        }
        NativeMethodInjectHelper.findMethod(mClassName, mMethodName)?.invoke(null, mParams.toString())
    }
}