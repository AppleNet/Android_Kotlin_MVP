package com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.presenter

import com.example.llcgs.android_kotlin.architecture_components.base.presenter.BaseArchPresenter

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.presenter.IExtendLiveDataPresenter
 * @author liulongchao
 * @since 2017/11/29
 */
interface IExtendLiveDataPresenter : BaseArchPresenter {

    fun getSeconds(l: Long)
}