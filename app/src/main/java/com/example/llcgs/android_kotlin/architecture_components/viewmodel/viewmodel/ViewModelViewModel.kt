package com.example.llcgs.android_kotlin.architecture_components.viewmodel.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.llcgs.android_kotlin.architecture_components.base.presenter.BaseArchPresenter
import com.example.llcgs.android_kotlin.architecture_components.viewmodel.model.ViewModelModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.architecture_components.viewmodel.viewmodel.ViewModelViewModel
 * @author liulongchao
 * @since 2017/11/30
 */
class ViewModelViewModel : ViewModel(), BaseArchPresenter {

    private var urlLiveData: MutableLiveData<String>? = null
    private val model = ViewModelModel()

    fun getUrlLiveData(): LiveData<String>? {
        if (urlLiveData == null) {
            urlLiveData = MutableLiveData()
            fetchUrl("")
        }
        return urlLiveData
    }

    fun fetchUrl(urlKey: String) {
        model.getUrl(urlKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (!urlKey.isEmpty()){
                        urlLiveData?.value = it
                    }
                }
    }


}