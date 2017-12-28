package com.example.llcgs.android_kotlin.design_pattern.wrapper.presenter.impl

import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.design_pattern.wrapper.model.WrapperDesignPatternModel
import com.example.llcgs.android_kotlin.design_pattern.wrapper.presenter.IWrapperDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.wrapper.view.WrapperDesignPatternView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.design_pattern.wrapper.presenter.impl.WrapperDesignPatternPresenter
 * @author liulongchao
 * @since 2017/12/27
 */
class WrapperDesignPatternPresenter(private val view: WrapperDesignPatternView) : IWrapperDesignPatternPresenter {

    private val model = WrapperDesignPatternModel()

    override fun login(userName: String, userPwd: String, pattern:String) {
        model.login(userName, userPwd, pattern)
                .doOnSubscribe {
                    view.addDisposable(it)
                }
                .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate {
                    view.dismissLoadingDialog()
                }
                .subscribe {
                    view.onGetLoginResult(it)
                }
    }
}