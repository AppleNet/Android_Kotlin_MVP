package com.example.llcgs.android_kotlin.architecture_components.livedata.use_livedata.presenter

import android.arch.lifecycle.MutableLiveData
import com.example.llcgs.android_kotlin.architecture_components.base.presenter.BaseArchPresenter
import com.example.llcgs.android_kotlin.architecture_components.livedata.bean.LiveDataBean

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.use_livedata.ILiveDataPresenter
 * @author liulongchao
 * @since 2017/11/28
 */
interface ILiveDataPresenter : BaseArchPresenter {

    fun fetchList(array: Array<String>)

}