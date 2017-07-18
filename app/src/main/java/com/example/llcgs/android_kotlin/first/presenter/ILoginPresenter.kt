package com.example.llcgs.android_kotlin.first.presenter

import com.example.llcgs.android_kotlin.first.bean.User

/**
 * ILoginPresenter
 * @author liulongchao
 * @since 2017/5/18
 */


interface ILoginPresenter {

    fun doLogin(user : User)

}