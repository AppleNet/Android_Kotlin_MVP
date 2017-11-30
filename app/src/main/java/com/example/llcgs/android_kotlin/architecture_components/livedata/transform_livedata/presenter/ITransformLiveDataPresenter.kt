package com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.presenter

import com.example.llcgs.android_kotlin.architecture_components.base.presenter.BaseArchPresenter

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.presenter.ITransformLiveDataPresenter
 * @author liulongchao
 * @since 2017/11/29
 */
interface ITransformLiveDataPresenter : BaseArchPresenter {

    fun getContents(array: Array<String>)

}