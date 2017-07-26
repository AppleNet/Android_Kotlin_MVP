package com.example.llcgs.android_kotlin.home.presenter

import com.example.llcgs.android_kotlin.home.bean.User

/**
 * ILoginPresenter
 * @author liulongchao
 * @since 2017/5/18
 */


interface ILoginPresenter {

    fun doLogin(user : User)

}