package com.example.llcgs.android_kotlin.base.network

import com.example.llcgs.android_kotlin.mvvm.list.model.StudentResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * com.example.llcgs.android_kotlin.base.network.ApiService
 * @author liulongchao
 * @since 2017/10/26
 */


interface ApiService {

    @GET
    fun fetchPeople(@Url url: String): Observable<StudentResponse>
}