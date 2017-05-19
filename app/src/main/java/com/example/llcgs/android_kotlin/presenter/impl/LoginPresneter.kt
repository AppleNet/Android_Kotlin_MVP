package com.example.llcgs.android_kotlin.presenter.impl

import com.example.llcgs.android_kotlin.base.BasePresenter
import com.example.llcgs.android_kotlin.bean.User
import com.example.llcgs.android_kotlin.model.LoginModel
import com.example.llcgs.android_kotlin.presenter.ILoginPresenter
import com.example.llcgs.android_kotlin.view.LoginView

/**
 * com.example.llcgs.android_kotlin.presenter.impl.LoginPresneter
 * @author liulongchao
 * @since 2017/5/18
 */


class LoginPresneter(loginView : LoginView) : BasePresenter<LoginView>(), ILoginPresenter {

    var loginView:LoginView = loginView

    override fun doLogin(user: User) {
        var model : LoginModel = LoginModel()
        var flag :Boolean = model.doLogin(user)!!
        if (flag){
            loginView.doLoginSuccess()
        }

    }
}