package com.example.llcgs.android_kotlin.design_pattern.flyweight.presenter.impl

import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.design_pattern.flyweight.bean.DesignPatternUser
import com.example.llcgs.android_kotlin.design_pattern.flyweight.model.FlyweightDesignPatternModel
import com.example.llcgs.android_kotlin.design_pattern.flyweight.presenter.IFlyweightDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.flyweight.view.FlyweightDesignPatternView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.design_pattern.flyweight.presenter.impl.FlyweightDesignPatternPresenter
 * @author liulongchao
 * @since 2017/12/26
 */
class FlyweightDesignPatternPresenter(private val view: FlyweightDesignPatternView): IFlyweightDesignPatternPresenter {

    private val model = FlyweightDesignPatternModel()

    override fun doLogin(user: DesignPatternUser) {
        model.doLogin(user)
                .doOnSubscribe {
                    view.addDisposable(it)
                }
                .subscribeOn(Schedulers.io())
                .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate {
                    view.dismissLoadingDialog()
                }
                .map {
                    it.isNotEmpty()
                }
                .subscribe {
                    view.onGetPwd(it)
                }
    }
}