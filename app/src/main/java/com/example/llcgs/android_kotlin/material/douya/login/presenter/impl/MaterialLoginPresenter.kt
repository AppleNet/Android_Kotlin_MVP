package com.example.llcgs.android_kotlin.material.douya.login.presenter.impl

import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.material.douya.login.model.MaterialLoginModel
import com.example.llcgs.android_kotlin.material.douya.login.presenter.IMaterialLoginPresenter
import com.example.llcgs.android_kotlin.material.douya.login.view.MaterialLoginView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.material.login.presenter.impl.MaterialLoginPresenter
 * @author liulongchao
 * @since 2017/12/11
 */
class MaterialLoginPresenter(private val view: MaterialLoginView) : IMaterialLoginPresenter {

    private val model = MaterialLoginModel()

    override fun login(userName: String, userPwd: String) {
        model.login(userName, userPwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.addDisposable(it)
                    view.showLoadingDialog()
                }
                .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
                .doOnTerminate {
                    view.dismissLoadingDialog()
                }
                .subscribe {
                    if (it){
                        view.onLoginSuccess()
                    }
                }
    }
}