package com.example.llcgs.android_kotlin.algorithms.cardinality

import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsActivity
import com.example.llcgs.android_kotlin.algorithms.cardinality.presenter.ICardinalitySortPresenter
import com.example.llcgs.android_kotlin.algorithms.cardinality.presenter.impl.CardinalitySortPresenter
import com.example.llcgs.android_kotlin.algorithms.cardinality.view.CardinalitySortView
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cardinality.*
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.algorithms.cardinality.CardinalitySortActivity
 * @author liulongchao
 * @since 2018/4/10
 */
class CardinalitySortActivity: BaseAlgorithmsActivity<ICardinalitySortPresenter>(), CardinalitySortView, View.OnClickListener {

    override fun createPresenter(): ICardinalitySortPresenter= CardinalitySortPresenter(this)

    override fun getLayoutId()= R.layout.activity_cardinality

    override fun initViews() {
        arrayText.text = stringBuilder.toString()
        button46.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button46 ->{
                mPresenter.cardSort(score)
            }
        }
    }

    override fun onGetCardSort(array: Array<Int>) {
        stringBuilder = StringBuilder()
        Observable.interval(1, TimeUnit.SECONDS)
            .take(array.size.toLong())
            .map {
                val tmp = array[it.toInt()]
                "$tmp,"
            }
            .doOnSubscribe {
                addDisposable(it)
            }
            .compose(bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                stringBuilder.append(it)
                arraySortText.text = stringBuilder.toString()
            }
    }

}