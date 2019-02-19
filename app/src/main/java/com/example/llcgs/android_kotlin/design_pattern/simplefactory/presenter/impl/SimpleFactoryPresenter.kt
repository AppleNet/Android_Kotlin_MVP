package com.example.llcgs.android_kotlin.design_pattern.simplefactory.presenter.impl;

import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.base.presenter.BasePresenter
import com.example.llcgs.android_kotlin.base.rx.MyObserver
import com.example.llcgs.android_kotlin.design_pattern.simplefactory.model.SimpleFactoryModel
import com.example.llcgs.android_kotlin.design_pattern.simplefactory.presenter.ISimpleFactoryPresenter
import com.example.llcgs.android_kotlin.design_pattern.simplefactory.view.SimpleFactoryView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.design_pattern.simplefactory.presenter.impl.SimpleFactoryPresenter
 * @author liulongchao
 * @since 2019/2/18
 */
class SimpleFactoryPresenter(val view: SimpleFactoryView): ISimpleFactoryPresenter, BasePresenter<SimpleFactoryView>() {

    val model = SimpleFactoryModel()

    override fun login(type: String) {
        model.login(type)
                .doOnSubscribe {
                    view.addDisposable(it)
                }
                .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate {
                    view.dismissLoadingDialog()
                }
                .subscribe(object : MyObserver<Boolean>(){
                    override fun onNext(t: Boolean) {

                    }
                })
    }
}
