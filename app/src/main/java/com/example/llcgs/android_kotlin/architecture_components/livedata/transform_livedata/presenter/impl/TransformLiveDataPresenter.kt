package com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.presenter.impl

import com.example.llcgs.android_kotlin.architecture_components.base.rx.LifeCycleRx
import com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.model.TransformLiveDataModel
import com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.presenter.ITransformLiveDataPresenter
import com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.view.TransformLiveDataView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.presenter.impl.TransformLiveDataPresenter
 * @author liulongchao
 * @since 2017/11/29
 */
class TransformLiveDataPresenter(val view: TransformLiveDataView) : ITransformLiveDataPresenter {

    val model = TransformLiveDataModel()

    override fun getContents(array: Array<String>) {
        model.getContents(array)
                .repeat()
                .doOnSubscribe {
                    view.addDisposable(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(LifeCycleRx.bindLifecycle(view))
                .subscribe {
                    view.onGetContent(it)
                }
    }


}