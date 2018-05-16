package com.example.llcgs.android_kotlin.algorithms.merge

import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsActivity
import com.example.llcgs.android_kotlin.algorithms.merge.presenter.IMergeSortPresenter
import com.example.llcgs.android_kotlin.algorithms.merge.presenter.impl.MergeSortPresenter
import com.example.llcgs.android_kotlin.algorithms.merge.view.MergeSortView
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_merge.*
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.algorithms.merge.MergeSortActivity
 * @author liulongchao
 * @since 2018/4/2
 */
class MergeSortActivity: BaseAlgorithmsActivity<IMergeSortPresenter>(), MergeSortView, View.OnClickListener {


    override fun createPresenter(): IMergeSortPresenter = MergeSortPresenter(this)

    override fun getLayoutId(): Int = R.layout.activity_merge

    override fun initViews() {
        arrayText.text = stringBuilder.toString()
        button45.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button45 ->{
                mPresenter.mergeSort(score)
            }
        }
    }

    override fun onGetMergeSort(array: Array<Int>) {
        stringBuilder = StringBuilder()
        Observable.interval(1, TimeUnit.SECONDS)
            .take(array.size.toLong())
            .doOnSubscribe {
                addDisposable(it)
            }
            .compose(bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .map {
                val tmp = array[it.toInt()]
                "$tmp,"
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                stringBuilder.append(it)
                arraySortText.text = stringBuilder.toString()
            }
    }
}