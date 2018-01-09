package com.example.llcgs.android_kotlin.material.animation.activity_options.model

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialModel
import com.example.llcgs.android_kotlin.utils.BaseUtil
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.material.animation.activity_options.model.ActivityOptionsModel
 * @author liulongchao
 * @since 2018/1/8
 */
class ActivityOptionsModel : BaseMaterialModel {


    fun getActivityOptions(): Observable<List<String>>{
        val array = BaseUtil.context().resources.getStringArray(R.array.activity_options).toMutableList()
        return Observable.just(array)
    }

}