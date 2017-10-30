package com.example.llcgs.android_kotlin.base.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * com.example.llcgs.android_kotlin.base.network.BaseFactory
 * @author liulongchao
 * @since 2017/10/26
 */


object BaseFactory {

    val BASE_URL = "http://api.randomuser.me/"
    val RANDOM_USER_URL = "http://api.randomuser.me/?results=10&nat=en"
    val PROJECT_URL = "https://github.com/erikcaffrey/People-MVVM"

    fun create(): ApiService {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create<ApiService>(ApiService::class.java)
    }
}