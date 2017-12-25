package com.example.llcgs.android_kotlin.material.base

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * com.example.llcgs.android_kotlin.material.base.MaterialFactory
 * @author liulongchao
 * @since 2017/12/25
 */
object MaterialFactory {

    val BASE = "https://www.douban.com/service/auth2/token"

    fun create(): MaterialService {
        val retrofit = Retrofit.Builder().baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create<MaterialService>(MaterialService::class.java)
    }
}