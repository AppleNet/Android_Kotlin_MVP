package com.example.llcgs.android_kotlin.net.webview.model

import com.example.llcgs.android_kotlin.net.base.BaseNetWorkModel
import io.reactivex.Observable
import org.json.JSONObject

/**
 * com.example.llcgs.android_kotlin.net.webview.model.WebViewJsBridgeModel
 * @author liulongchao
 * @since 2018/5/9
 */
class WebViewJsBridgeModel: BaseNetWorkModel {

    fun getToken(jsonStr: String): Observable<String>{

        val data = JSONObject(jsonStr)
        val msg = data.optString("msg")
        if (msg == "Toast"){
            return Observable.just("Kobe")
        }
        return Observable.just("McGrady")
    }

}