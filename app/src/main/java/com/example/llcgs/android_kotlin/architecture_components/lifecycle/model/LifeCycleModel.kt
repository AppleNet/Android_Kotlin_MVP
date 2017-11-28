package com.example.llcgs.android_kotlin.architecture_components.lifecycle.model

import com.example.llcgs.android_kotlin.architecture_components.base.model.BaseArchModel
import com.example.llcgs.android_kotlin.architecture_components.lifecycle.bean.Login
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.architecture_components.lifecycle.model.LifeCycleModel
 * @author liulongchao
 * @since 2017/11/27
 */
class LifeCycleModel : BaseArchModel {

    fun doLogin(login: Login): Observable<Login> = Observable.just(login).delay(5, TimeUnit.SECONDS)
}