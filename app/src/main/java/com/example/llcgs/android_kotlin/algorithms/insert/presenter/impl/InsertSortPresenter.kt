package com.example.llcgs.android_kotlin.algorithms.insert.presenter.impl

import com.example.llcgs.android_kotlin.algorithms.insert.model.InsertSortModel
import com.example.llcgs.android_kotlin.algorithms.insert.presenter.IInsertSortPresenter
import com.example.llcgs.android_kotlin.algorithms.insert.view.InsertSortView
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.algorithms.insert.presenter.impl.InsertSortPresenter
 * @author liulongchao
 * @since 2018/3/29
 */
class InsertSortPresenter(private var view: InsertSortView): IInsertSortPresenter {

    private val model = InsertSortModel()

    override fun insertSort(array: Array<Int>) {
        model.insertSort(array)
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onGetSortResult(it)
            }

    }

    override fun secondInsertSort(array: Array<Int>) {
        model.secondInsertSort(array)
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onGetSecondSortResult(it)
            }

    }
}