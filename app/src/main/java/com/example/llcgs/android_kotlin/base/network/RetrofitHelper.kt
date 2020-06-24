package com.example.llcgs.android_kotlin.base.network

import android.content.Context
import com.example.llcgs.android_kotlin.base.network.interceptor.HttpLogInterceptor
import com.example.llcgs.android_kotlin.base.network.interceptor.SimpleHttpIgnoredKeyProvider
import com.example.llcgs.android_kotlin.utils.BaseUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.base.network.RetrofitHelper
 * @author liulongchao
 * @since 2017/10/26
 */
object RetrofitHelper {

    val BASE_URL = "http://api.randomuser.me/"
    val RANDOM_USER_BASE_URL = "http://api.randomuser.me/?results=10&nat=en"
    val GITHUB_BASE_URL = "http://api.github.com/"
    val PROJECT_URL = "https://github.com/erikcaffrey/People-MVVM"

    private fun getContext(): Context = BaseUtil.context()

    /**
     *  获取基础Service
     *
     * */
    fun getService(url: String = BASE_URL): ApiService =
            getRetrofitBuilderCommon().baseUrl(url).build().create(ApiService::class.java)

    /**
     *  获取自定义Service
     *
     * */
    fun <T> getService(baseUrl: String = BASE_URL, service: Class<T>): T =
            getRetrofitBuilderCommon().baseUrl(baseUrl).build().create(service)

    /**
     *  获取带请求进度的基础Service
     *
     * */
    fun getProgressService(url: String = BASE_URL, requestBody: ProgressRequestBody): ApiService =
            getRetrofitBuilderProgress(requestBody).baseUrl(url).build().create(ApiService::class.java)

    /**
     *  获取带请求进度的自定义Service
     *
     * */
    fun <T> getProgressService(url: String = BASE_URL, requestBody: ProgressRequestBody, clazz: Class<T>): T =
            getRetrofitBuilderProgress(requestBody).baseUrl(url).build().create(clazz)

    /**
     *  获取带加密认证的基础Service
     *
     * */
    fun getSslService(url: String = BASE_URL): ApiService =
            getRetrofitBuilderSsl().baseUrl(url).build().create(ApiService::class.java)

    /**
     *  获取带加密认证的自定义Service
     *
     * */
    fun <T> getSslService(url: String = BASE_URL, clazz: Class<T>): T =
            getRetrofitBuilderSsl().baseUrl(url).build().create(clazz)

    /**
     *  获取基础RetrofitBuilder
     *
     * */
    private fun getRetrofitBuilderCommon(): Retrofit.Builder {
        return Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    }

    /**
     *  获取SSL RetrofitBuilder
     *
     * */
    private fun getRetrofitBuilderSsl(): Retrofit.Builder {
        try {
            val sslParams: HttpsUtils.SSLParams = HttpsUtils.getSslSocketFactory(null, null, getContext().assets.open("gome.cer"))
            val okHttpBuilder = OkHttpClient.Builder()
            okHttpBuilder.connectTimeout(40 * 1000, TimeUnit.MILLISECONDS)
            okHttpBuilder.readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
            okHttpBuilder.writeTimeout(20 * 1000, TimeUnit.MILLISECONDS)
            okHttpBuilder.retryOnConnectionFailure(true)
            okHttpBuilder.addInterceptor(HttpLogInterceptor.setIgnoredKeyProvider(SimpleHttpIgnoredKeyProvider()))
            val client = okHttpBuilder.hostnameVerifier { _, _ -> true }
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager).build()
            return Retrofit.Builder().client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Retrofit.Builder()
    }

    /**
     *  获取Progress RetrofitBuilder
     *
     * */
    private fun getRetrofitBuilderProgress(progressRequestBody: ProgressRequestBody): Retrofit.Builder {
        try {
            val sslParams: HttpsUtils.SSLParams = HttpsUtils.getSslSocketFactory(null, null, getContext().assets.open("gome.cer"))
            val okHttpBuilder = OkHttpClient.Builder()
            okHttpBuilder.connectTimeout(40 * 1000, TimeUnit.MILLISECONDS)
            okHttpBuilder.readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
            okHttpBuilder.writeTimeout(20 * 1000, TimeUnit.MILLISECONDS)
            okHttpBuilder.retryOnConnectionFailure(true)
            okHttpBuilder.addInterceptor(HttpLogInterceptor.setIgnoredKeyProvider(SimpleHttpIgnoredKeyProvider()))
            okHttpBuilder.addInterceptor(Interceptor { chain ->
                val originalRequest = chain.request()
                if (originalRequest.body() == null) {
                    return@Interceptor chain.proceed(originalRequest)
                }
                progressRequestBody.setDelegate(originalRequest.body())
                val progressRequest = originalRequest.newBuilder().method(originalRequest.method(), progressRequestBody).build()
                chain.proceed(progressRequest)
            })

            val client = okHttpBuilder.hostnameVerifier { _, _ -> true }
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager).build()
            return Retrofit.Builder().client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Retrofit.Builder()
    }

}