package com.example.llcgs.android_kotlin.algorithms.fast

import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsActivity
import com.example.llcgs.android_kotlin.algorithms.fast.presenter.IFastSortPresenter
import com.example.llcgs.android_kotlin.algorithms.fast.presenter.impl.FastSortPresenter
import com.example.llcgs.android_kotlin.algorithms.fast.view.FastSortView
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_fast.*
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.algorithms.fast.FastSortActivity
 * @author liulongchao
 * @since 2018/4/2
 */
class FastSortActivity: BaseAlgorithmsActivity<IFastSortPresenter>(), FastSortView, View.OnClickListener {

    override fun createPresenter(): IFastSortPresenter = FastSortPresenter(this)

    override fun getLayoutId()= R.layout.activity_fast

    override fun initViews() {
        arrayText.text = stringBuilder.toString()
        button44.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button44 ->{
                mPresenter.fastSort(score)
            }
        }
    }

    override fun onGetFastSort(array: Array<Int>) {
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