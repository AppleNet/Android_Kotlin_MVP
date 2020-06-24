package com.example.llcgs.android_kotlin.home.presenter.impl

import com.example.llcgs.android_kotlin.base.presenter.BasePresenter
import com.example.llcgs.android_kotlin.home.bean.User
import com.example.llcgs.android_kotlin.home.model.LoginModel
import com.example.llcgs.android_kotlin.home.presenter.ILoginPresenter
import com.example.llcgs.android_kotlin.home.view.LoginView

/**
 * LoginPresenter
 * @author liulongchao
 * @since 2017/5/18
 */


class LoginPresenter(private var loginView: LoginView) : BasePresenter<LoginView>(), ILoginPresenter {

    override fun doLogin(user: User) {
        val model = LoginModel()
        val flag :Boolean = model.doLogin(user)?: false
        if (flag){
            loginView.doLoginSuccess()
        }
    }

}