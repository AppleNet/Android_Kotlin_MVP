package com.example.llcgs.android_kotlin.architecture_components.livedata.presenter.impl

import android.arch.lifecycle.MutableLiveData
import com.example.llcgs.android_kotlin.architecture_components.base.rx.LifeCycleRx
import com.example.llcgs.android_kotlin.architecture_components.livedata.bean.LiveDataBean
import com.example.llcgs.android_kotlin.architecture_components.livedata.model.LiveDataModel
import com.example.llcgs.android_kotlin.architecture_components.livedata.presenter.ILiveDataPresenter
import com.example.llcgs.android_kotlin.architecture_components.livedata.view.LiveDataView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.presenter.impl.LiveDataPresenter
 * @author liulongchao
 * @since 2017/11/28
 */
class LiveDataPresenter(private val view: LiveDataView) : ILiveDataPresenter {

    private val model = LiveDataModel()

    override fun fetchList(array: Array<String>) {
        model.fetchList(array)
                .delay(2, TimeUnit.SECONDS)
                .doOnSubscribe {
                    view.addDisposable(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(LifeCycleRx.bindLifecycle(view))
                .subscribe {
                    view.onGetMutableLiveData(it)
                }
    }


}