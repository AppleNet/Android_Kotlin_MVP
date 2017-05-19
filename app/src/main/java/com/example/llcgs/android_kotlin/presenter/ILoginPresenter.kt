package com.example.llcgs.android_kotlin.presenter

import com.example.llcgs.android_kotlin.bean.User

/**
 * com.example.llcgs.android_kotlin.presenter.ILoginPresenter
 * @author liulongchao
 * @since 2017/5/18
 */


interface ILoginPresenter {

    fun doLogin(user : User)

}