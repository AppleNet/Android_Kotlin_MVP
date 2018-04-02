package com.example.llcgs.android_kotlin.algorithms.heap

import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsActivity
import com.example.llcgs.android_kotlin.algorithms.heap.presenter.IHeapSortPresenter
import com.example.llcgs.android_kotlin.algorithms.heap.presenter.impl.HeapSortPresenter
import com.example.llcgs.android_kotlin.algorithms.heap.view.HeapSortView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_heap.*
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.algorithms.heap.HeapSortActivity
 * @author liulongchao
 * @since 2018/3/30
 */
class HeapSortActivity: BaseAlgorithmsActivity<IHeapSortPresenter>(), HeapSortView, View.OnClickListener {

    override fun createPresenter(): IHeapSortPresenter = HeapSortPresenter(this)

    override fun getLayoutId()= R.layout.activity_heap

    override fun initViews() {
        arrayText.text = stringBuilder.toString()
        button43.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button43 ->{
                mPresenter.heapSort(score)
            }
        }
    }

    override fun onGetHeapSort(array: Array<Int>) {
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                stringBuilder.append(it)
                arraySortText.text = stringBuilder.toString()
            }
    }
}