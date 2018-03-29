package com.example.llcgs.android_kotlin.algorithms.insert

import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsActivity
import com.example.llcgs.android_kotlin.algorithms.insert.presenter.IInsertSortPresenter
import com.example.llcgs.android_kotlin.algorithms.insert.presenter.impl.InsertSortPresenter
import com.example.llcgs.android_kotlin.algorithms.insert.view.InsertSortView
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_insert.*
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.algorithms.insert.InsertSortActivity
 * @author liulongchao
 * @since 2018/3/29
 */
class InsertSortActivity: BaseAlgorithmsActivity<IInsertSortPresenter>(), View.OnClickListener, InsertSortView {


    override fun createPresenter(): IInsertSortPresenter= InsertSortPresenter(this)

    override fun getLayoutId()= R.layout.activity_insert

    override fun initViews() {
        arrayText.text = stringBuilder.toString()
        button39.setOnClickListener(this)
        button40.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button39 ->{
                mPresenter.insertSort(score)
            }
            R.id.button40 ->{
                mPresenter.secondInsertSort(score)
            }
        }
    }

    override fun onGetSortResult(array: Array<Int>) {
        stringBuilder = StringBuilder()
        showSortArray(array)

    }

    override fun onGetSecondSortResult(array: Array<Int>) {
        stringBuilder = StringBuilder()
        showSortArray(array)
    }

    private fun showSortArray(array: Array<Int>) {
        Observable.interval(1, TimeUnit.SECONDS)
            .take(array.size.toLong())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                addDisposable(it)
            }
            .compose(bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .map {
                val temp = array[it.toInt()]
                "$temp,"
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                stringBuilder.append(it)
                arraySortText.text = stringBuilder.toString()
            }
    }

}