package com.example.llcgs.android_kotlin.algorithms.heap.presenter.impl

import com.example.llcgs.android_kotlin.algorithms.heap.model.HeapSortModel
import com.example.llcgs.android_kotlin.algorithms.heap.presenter.IHeapSortPresenter
import com.example.llcgs.android_kotlin.algorithms.heap.view.HeapSortView
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.algorithms.heap.presenter.impl.HeapSortPresenter
 * @author liulongchao
 * @since 2018/3/30
 */
class HeapSortPresenter(private var view: HeapSortView): IHeapSortPresenter {

    private val model = HeapSortModel()

    override fun heapSort(array: Array<Int>) {
        model.heapSort(array)
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onGetHeapSort(it)
            }
    }
}