package com.example.llcgs.android_kotlin.architecture_components.livedata.model

import com.example.llcgs.android_kotlin.architecture_components.base.model.BaseArchModel
import com.example.llcgs.android_kotlin.architecture_components.livedata.bean.LiveDataBean
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.model.LiveDataModel
 * @author liulongchao
 * @since 2017/11/28
 */
class LiveDataModel:BaseArchModel {

    fun fetchList(arrayList: Array<String>): Observable<List<LiveDataBean>>{
        val list = ArrayList<LiveDataBean>()
        var dataBean:LiveDataBean
        arrayList.forEachIndexed { index, s ->
            dataBean = LiveDataBean()
            dataBean.id = index.toString()
            dataBean.content = s
            list.add(dataBean)
        }
        return Observable.just(list)
    }

}