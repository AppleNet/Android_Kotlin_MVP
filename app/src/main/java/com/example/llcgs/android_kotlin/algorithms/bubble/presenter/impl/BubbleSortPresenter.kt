package com.example.llcgs.android_kotlin.algorithms.bubble.presenter.impl

import com.example.llcgs.android_kotlin.algorithms.bubble.model.BubbleSortModel
import com.example.llcgs.android_kotlin.algorithms.bubble.presenter.IBubbleSortPresenter
import com.example.llcgs.android_kotlin.algorithms.bubble.view.BubbleSortView
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.base.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.algorithms.bubble.presenter.impl.BubbleSortPresenter
 * @author liulongchao
 * @since 2018/3/28
 */
class BubbleSortPresenter(var view: BubbleSortView): IBubbleSortPresenter {

    private val model = BubbleSortModel()

    override fun bubbleSort(array: Array<Int>) {
        model.bubbleSort(array)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribe {
                view.onGetBubbleSortArray(it)
            }
    }

}