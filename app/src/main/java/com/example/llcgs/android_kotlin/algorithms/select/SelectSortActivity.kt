package com.example.llcgs.android_kotlin.algorithms.select

import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsActivity
import com.example.llcgs.android_kotlin.algorithms.select.presenter.ISelectSortPresenter
import com.example.llcgs.android_kotlin.algorithms.select.presenter.impl.SelectSortPresenter
import com.example.llcgs.android_kotlin.algorithms.select.view.SelectSortView
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_select.*
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.algorithms.select.SelectSortActivity
 * @author liulongchao
 * @since 2018/3/30
 */
class SelectSortActivity: BaseAlgorithmsActivity<ISelectSortPresenter>(), SelectSortView, View.OnClickListener {

    override fun createPresenter(): ISelectSortPresenter= SelectSortPresenter(this)

    override fun getLayoutId()= R.layout.activity_select

    override fun initViews() {
        arrayText.text = stringBuilder.toString()
        button42.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button42 ->{
                mPresenter.selectSort(score)
            }
        }
    }

    override fun onGetSelectSort(array: Array<Int>) {
        stringBuilder = StringBuilder()
        Observable.interval(1, TimeUnit.SECONDS)
            .take(array.size.toLong())
            .doOnSubscribe {
                addDisposable(it)
            }
            .compose(bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .map {
                val temp = array[it.toInt()]
                "$temp,"
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                stringBuilder.append(it)
                arraySortText.text = stringBuilder.toString()
            }
    }

}