package com.example.llcgs.android_kotlin.algorithms.shell

import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsActivity
import com.example.llcgs.android_kotlin.algorithms.shell.presenter.IShellSortPresenter
import com.example.llcgs.android_kotlin.algorithms.shell.presenter.impl.ShellSortPresenter
import com.example.llcgs.android_kotlin.algorithms.shell.view.ShellSortView
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_shell.*
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.algorithms.shell.ShellSortActivity
 * @author liulongchao
 * @since 2018/3/29
 */
class ShellSortActivity: BaseAlgorithmsActivity<IShellSortPresenter>(), ShellSortView, View.OnClickListener {

    override fun createPresenter(): IShellSortPresenter = ShellSortPresenter(this)

    override fun getLayoutId()= R.layout.activity_shell

    override fun initViews() {
        arrayText.text = stringBuilder.toString()
        button41.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button41 ->{
                mPresenter.shellSort(score)
            }
        }
    }

    override fun onGetShellSort(array: Array<Int>) {
        stringBuilder = StringBuilder()
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
