package com.example.llcgs.android_kotlin.base.network

import com.example.llcgs.android_kotlin.mvvm.list.model.StudentResponse
import com.example.llcgs.android_kotlin.net.rss.bean.retrofit.XmlRss
import io.reactivex.Observable
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
}