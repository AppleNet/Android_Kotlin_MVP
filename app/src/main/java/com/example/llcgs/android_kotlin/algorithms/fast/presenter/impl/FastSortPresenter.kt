package com.example.llcgs.android_kotlin.algorithms.fast.presenter.impl

import com.example.llcgs.android_kotlin.algorithms.fast.model.FastSortModel
import com.example.llcgs.android_kotlin.algorithms.fast.presenter.IFastSortPresenter
import com.example.llcgs.android_kotlin.algorithms.fast.view.FastSortView
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.algorithms.fast.presenter.impl.FastSortPresenter
 * @author liulongchao
 * @since 2018/4/2
 */
class FastSortPresenter(private var view: FastSortView): IFastSortPresenter {

    private val model = FastSortModel()

    override fun fastSort(array: Array<Int>) {
        model.fastSort(array)
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribe {
                view.onGetFastSort(it)
            }
    }
}