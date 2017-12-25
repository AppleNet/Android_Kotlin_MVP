package com.example.llcgs.android_kotlin.material.base

import com.example.llcgs.android_kotlin.base.rx.bean.WrapperBean
import com.example.llcgs.android_kotlin.material.login.bean.LoginResponse
import io.reactivex.Observable
import retrofit2.http.*

/**
 * com.example.llcgs.android_kotlin.material.base.MaterialService
 * @author liulongchao
 * @since 2017/12/25
 */
interface MaterialService {

    @POST
    @FormUrlEncoded
    fun login(@Header("User-Agent")userAgent:String,
              @Header("Accept-Charset")acceptCharset:String,
              @QueryMap params: Map<String, String>): Observable<WrapperBean<LoginResponse>>

}