package com.example.llcgs.android_kotlin.algorithms.shell.presenter.impl

import com.example.llcgs.android_kotlin.algorithms.shell.model.ShellSortModel
import com.example.llcgs.android_kotlin.algorithms.shell.presenter.IShellSortPresenter
import com.example.llcgs.android_kotlin.algorithms.shell.view.ShellSortView
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.algorithms.shell.presenter.impl.ShellSortPresenter
 * @author liulongchao
 * @since 2018/3/29
 */
class ShellSortPresenter(private val view: ShellSortView): IShellSortPresenter {

    private val model = ShellSortModel()

    override fun shellSort(array: Array<Int>) {
        model.shellSort(array)
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onGetShellSort(it)
            }
    }

}