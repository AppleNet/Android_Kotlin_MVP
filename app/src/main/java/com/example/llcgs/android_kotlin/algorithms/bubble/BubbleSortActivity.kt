package com.example.llcgs.android_kotlin.algorithms.bubble

import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsActivity
import com.example.llcgs.android_kotlin.algorithms.bubble.presenter.IBubbleSortPresenter
import com.example.llcgs.android_kotlin.algorithms.bubble.presenter.impl.BubbleSortPresenter
import com.example.llcgs.android_kotlin.algorithms.bubble.view.BubbleSortView
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_bubble.*
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.algorithms.bubble.BubbleSortActivity
 * @author liulongchao
 * @since 2018/3/28
 */

class BubbleSortActivity: BaseAlgorithmsActivity<IBubbleSortPresenter>(), View.OnClickListener, BubbleSortView {

    private val score : Array<Int> = arrayOf(67, 75, 69, 89, 87, 99, 90, 100, 1)

    override fun createPresenter()= BubbleSortPresenter(this)

    override fun getLayoutId()= R.layout.activity_bubble

    override fun initViews() {
        val sb = StringBuilder()
        score.forEach {
            sb.append(it)
            sb.append(",")
        }
        arrayText.text = sb.toString()
        button38.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button38 ->{
                // 排序
                mPresenter.bubbleSort(score)
            }
        }
    }

    override fun onGetBubbleSortArray(array: Array<Int>) {
        val sb = StringBuilder()
        Observable.interval(1, TimeUnit.SECONDS)
            .take(array.size.toLong())
            .doOnSubscribe {
                addDisposable(it)
            }
            // 订阅
            .subscribeOn(Schedulers.io())
            // 观察
            .observeOn(AndroidSchedulers.mainThread())
            .compose(bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .map {
                val temp = array[it.toInt()]
                "$temp,"
            }
            .subscribe {
                sb.append(it)
                arraySortText.text = sb.toString()
            }
    }
}