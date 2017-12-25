package com.example.llcgs.android_kotlin.material.login.model

import android.os.Build
import com.example.llcgs.android_kotlin.base.rx.bean.WrapperBean
import com.example.llcgs.android_kotlin.material.base.BaseMaterialModel
import com.example.llcgs.android_kotlin.material.base.MaterialFactory
import com.example.llcgs.android_kotlin.material.login.bean.LoginResponse
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.material.login.model.MaterialLoginModel
 * @author liulongchao
 * @since 2017/12/11
 */
class MaterialLoginModel : BaseMaterialModel {

    fun login(userName: String, userPwd: String): Observable<Boolean> {
        if (userName.equals( "Kobe", true) && userPwd.equals("123456", true)){
            return Observable.just(true)
        }
        return Observable.just(false)
    }

    fun doLogin(userName: String, userPwd: String): Observable<WrapperBean<LoginResponse>>{
        val params = HashMap<String, String>()
        params.put("client_id", "key_frodo_api_key")
        params.put("client_secret", "key_frodo_api_secret")
        params.put("redirect_uri", "frodo://app/oauth/callback/")
        params.put("grant_type", "password")
        params.put("username", userName)
        params.put("password", userPwd)
        return MaterialFactory.create().login("api-client/Volley/1 com.douban.frodo/5.12.0(115) Android/"+ Build.VERSION.SDK_INT+ " " + Build.PRODUCT + " " + Build.MANUFACTURER + " "
                + Build.MODEL + "  rom:android","UTF-8", params)
    }

}