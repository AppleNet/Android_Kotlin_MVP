package com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.presenter.impl

import com.example.llcgs.android_kotlin.architecture_components.base.rx.LifeCycleRx
import com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.model.ExtendLiveDataModel
import com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.presenter.IExtendLiveDataPresenter
import com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.view.ExtendLiveDataView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.presenter.impl.ExtendLiveDataPresenter
 * @author liulongchao
 * @since 2017/11/29
 */
class ExtendLiveDataPresenter(private val view: ExtendLiveDataView):IExtendLiveDataPresenter {

    private val model = ExtendLiveDataModel()

    override fun getSeconds(l: Long) {
        model.getSeconds()
                .take(61)
                .repeat()
                .startWith(l)
                .doOnSubscribe { view.addDisposable(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(LifeCycleRx.bindLifecycle(view))
                .subscribe {
                    view.onGetSeconds(it)
                }
    }
}