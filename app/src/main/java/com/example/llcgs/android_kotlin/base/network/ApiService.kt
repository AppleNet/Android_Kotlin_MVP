package com.example.llcgs.android_kotlin.base.network

import com.example.llcgs.android_kotlin.kotlin.variable.bean.Repo
import com.example.llcgs.android_kotlin.mvvm.list.model.StudentResponse
import com.example.llcgs.android_kotlin.net.rss.bean.retrofit.XmlRss
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

/**
 * com.example.llcgs.android_kotlin.base.network.ApiService
 * @author liulongchao
 * @since 2017/10/26
 */

interface ApiService {

    @GET
    fun fetchPeople(@Url url: String): Observable<StudentResponse>

    @POST("xml/news-0.aspx?news=0")
    fun getRssContent(): Observable<XmlRss>

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Repo>>

    // Retrofit 对协程的支持，也就是说 使用suspend标记一个函数，便可以将此方法放到协程中，并且不需要声明withContext来切换线程，其内部实现切换
    @GET("users/{user}/repos")
    suspend fun listReposRetrofit(@Path("user") user: String): List<Repo>

    @GET("users/{user}/repos")
    fun listReposRxJava(@Path("user") user: String): Single<List<Repo>>
}