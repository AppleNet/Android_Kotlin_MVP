package com.example.llcgs.android_kotlin.architecture_components.livedata.use_livedata.view

import android.arch.lifecycle.MutableLiveData
import com.example.llcgs.android_kotlin.architecture_components.base.view.BaseArchView
import com.example.llcgs.android_kotlin.architecture_components.livedata.bean.LiveDataBean

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.use_livedata.LiveDataView
 * @author liulongchao
 * @since 2017/11/28
 */
interface LiveDataView:BaseArchView {

    fun onGetMutableLiveData(list: List<LiveDataBean>)
}