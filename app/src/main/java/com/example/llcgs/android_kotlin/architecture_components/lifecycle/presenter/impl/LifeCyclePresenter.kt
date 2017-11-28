package com.example.llcgs.android_kotlin.architecture_components.lifecycle.presenter.impl

import com.example.llcgs.android_kotlin.architecture_components.lifecycle.bean.Login
import com.example.llcgs.android_kotlin.architecture_components.lifecycle.model.LifeCycleModel
import com.example.llcgs.android_kotlin.architecture_components.lifecycle.presenter.ILifecyclePresenter
import com.example.llcgs.android_kotlin.architecture_components.base.rx.LifeCycleRx
import com.example.llcgs.android_kotlin.architecture_components.lifecycle.view.LifeCycleView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.architecture_components.lifecycle.presenter.impl.LifeCyclePresenter
 * @author liulongchao
 * @since 2017/11/27
 */
class LifeCyclePresenter(private val view:LifeCycleView): ILifecyclePresenter {

    private val model = LifeCycleModel()

    override fun doLogin(login: Login) {
        model.doLogin(login)
                .doOnSubscribe {
                    view.addDisposable(it)
                }
                .flatMap({
                    var flag = false
                    if (login.name == "James" && login.pwd =="33"){
                        flag = true
                    }
                    Observable.just(flag)
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(LifeCycleRx.bindLifecycle(view))
                .subscribe{
                    view.onLoginSuccess(it)
                }
    }


}