package com.example.llcgs.android_kotlin.architecture_components.livedata.use_livedata.presenter.impl

import com.example.llcgs.android_kotlin.architecture_components.base.rx.LifeCycleRx
import com.example.llcgs.android_kotlin.architecture_components.livedata.use_livedata.model.LiveDataModel
import com.example.llcgs.android_kotlin.architecture_components.livedata.use_livedata.presenter.ILiveDataPresenter
import com.example.llcgs.android_kotlin.architecture_components.livedata.use_livedata.view.LiveDataView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.use_livedata.LiveDataPresenter
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