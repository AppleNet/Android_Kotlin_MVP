package com.example.llcgs.android_kotlin.algorithms.select.presenter.impl

import com.example.llcgs.android_kotlin.algorithms.select.model.SelectSortModel
import com.example.llcgs.android_kotlin.algorithms.select.presenter.ISelectSortPresenter
import com.example.llcgs.android_kotlin.algorithms.select.view.SelectSortView
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.algorithms.select.presenter.impl.SelectSortPresenter
 * @author liulongchao
 * @since 2018/3/30
 */
class SelectSortPresenter(private var view: SelectSortView): ISelectSortPresenter {

    private val model = SelectSortModel()

    override fun selectSort(array: Array<Int>) {
        model.selectSort(array)
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onGetSelectSort(it)
            }
    }
}