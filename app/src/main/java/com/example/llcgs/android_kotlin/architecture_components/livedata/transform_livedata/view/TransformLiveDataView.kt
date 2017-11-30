package com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.view

import com.example.llcgs.android_kotlin.architecture_components.base.view.BaseArchView

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.view.TransformLiveDataView
 * @author liulongchao
 * @since 2017/11/29
 */
interface TransformLiveDataView:BaseArchView {

    fun onGetContent(string: String)
}