package com.example.llcgs.android_kotlin.first.presenter.impl

import com.example.llcgs.android_kotlin.base.presenter.BasePresenter
import com.example.llcgs.android_kotlin.first.bean.User
import com.example.llcgs.android_kotlin.first.model.LoginModel
import com.example.llcgs.android_kotlin.first.presenter.ILoginPresenter
import com.example.llcgs.android_kotlin.first.view.LoginView

/**
 * LoginPresneter
 * @author liulongchao
 * @since 2017/5/18
 */


class LoginPresneter(loginView : LoginView) : BasePresenter<LoginView>(), ILoginPresenter {

    var loginView: LoginView = loginView

    override fun doLogin(user: User) {
        var model : LoginModel = LoginModel()
        var flag :Boolean = model.doLogin(user)?: false
        if (flag){
            loginView.doLoginSuccess()
        }

    }
}