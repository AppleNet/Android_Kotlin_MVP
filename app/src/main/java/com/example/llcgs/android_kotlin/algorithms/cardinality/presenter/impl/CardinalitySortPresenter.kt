package com.example.llcgs.android_kotlin.algorithms.cardinality.presenter.impl

import com.example.llcgs.android_kotlin.algorithms.cardinality.model.CardinalitySortModel
import com.example.llcgs.android_kotlin.algorithms.cardinality.presenter.ICardinalitySortPresenter
import com.example.llcgs.android_kotlin.algorithms.cardinality.view.CardinalitySortView
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.algorithms.cardinality.presenter.impl.CardinalitySortPresenter
 * @author liulongchao
 * @since 2018/4/10
 */
class CardinalitySortPresenter(private val view: CardinalitySortView): ICardinalitySortPresenter {

    private val model = CardinalitySortModel()

    override fun cardSort(array: Array<Int>) {
        model.cardSort(array.toIntArray())
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onGetCardSort(it.toTypedArray())
            }
    }
}