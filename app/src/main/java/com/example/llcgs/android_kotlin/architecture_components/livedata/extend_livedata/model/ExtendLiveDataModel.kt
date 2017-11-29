package com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.model

import com.example.llcgs.android_kotlin.architecture_components.base.model.BaseArchModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.model.ExtendLiveDataModel
 * @author liulongchao
 * @since 2017/11/29
 */
class ExtendLiveDataModel : BaseArchModel {

    fun getSeconds(): Observable<Long> = Observable.interval(1, TimeUnit.SECONDS)

}