package com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.llcgs.android_kotlin.architecture_components.base.presenter.BaseArchPresenter
import com.example.llcgs.android_kotlin.base.presenter.SuperPresenter

/**
 * com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.viewmodel.ShareViewModel
 * @author liulongchao
 * @since 2017/12/1
 */
class ShareViewModel : ViewModel(), BaseArchPresenter, SuperPresenter {

    private var liveData = MutableLiveData<List<String>>()

    fun setData(array: List<String>){
        liveData.value = array
    }

    fun fetchList() = liveData
}