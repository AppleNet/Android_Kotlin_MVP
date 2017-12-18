package com.example.llcgs.android_kotlin.material.detail.model

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialModel
import com.example.llcgs.android_kotlin.utils.BaseUtil
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.material.detail.model.DetailModel
 * @author liulongchao
 * @since 2017/12/14
 */
class DetailModel : BaseMaterialModel {

    fun loadComments(): Observable<List<String>>{
        val array = BaseUtil.context().resources.getStringArray(R.array.databinding_nba).toMutableList()
        return Observable.just(array)
    }
}