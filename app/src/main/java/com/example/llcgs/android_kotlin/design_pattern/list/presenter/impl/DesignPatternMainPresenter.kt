package com.example.llcgs.android_kotlin.design_pattern.list.presenter.impl

import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.design_pattern.list.model.DesignPatternMainModel
import com.example.llcgs.android_kotlin.design_pattern.list.presenter.IDesignPatternMainPresenter
import com.example.llcgs.android_kotlin.design_pattern.list.view.DesignPatternMainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.design_pattern.list.presenter.impl.DesignPatternMainPresenter
 * @author liulongchao
 * @since 2017/12/26
 */
class DesignPatternMainPresenter(private val view: DesignPatternMainView) : IDesignPatternMainPresenter {

    private val model = DesignPatternMainModel()

    override fun fetchDesignPatter() {
        model.fetchDesignPattern()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.addDisposable(it)
                }
                .doOnTerminate {
                    view.dismissLoadingDialog()
                }
                .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
                .subscribe {
                    view.onGetDesignPattern(it)
                }
    }
}