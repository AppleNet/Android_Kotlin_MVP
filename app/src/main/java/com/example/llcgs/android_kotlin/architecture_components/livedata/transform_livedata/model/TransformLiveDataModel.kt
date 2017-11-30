package com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.model

import com.example.llcgs.android_kotlin.architecture_components.base.model.BaseArchModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.model.TransformLiveDataModel
 * @author liulongchao
 * @since 2017/11/29
 */
class TransformLiveDataModel : BaseArchModel {

    fun getContents(array: Array<String>): Observable<String> =
            Observable.interval(1, TimeUnit.SECONDS)
                    .flatMap({ Observable.just(array[it.toInt()]) })
                    .take(array.size.toLong())

}