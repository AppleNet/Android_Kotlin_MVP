package com.example.llcgs.android_kotlin.design_pattern.list.model

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternModel
import com.example.llcgs.android_kotlin.utils.BaseUtil
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.design_pattern.list.model.DesignPatternMainModel
 * @author liulongchao
 * @since 2017/12/26
 */
class DesignPatternMainModel : BaseDesignPatternModel {

    fun fetchDesignPattern(): Observable<List<String>>{
        val array = BaseUtil.context().resources.getStringArray(R.array.design_pattern).toMutableList()
        return Observable.just(array)
    }

}