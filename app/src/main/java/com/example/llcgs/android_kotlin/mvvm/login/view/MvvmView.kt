package com.example.llcgs.android_kotlin.mvvm.login.view

import com.example.llcgs.android_kotlin.mvvm.base.BaseView
import com.example.llcgs.android_kotlin.mvvm.login.model.User


/**
 * com.example.llcgs.android_kotlin.mvvm.login.MvvmView
 * @author liulongchao
 * @since 2017/10/25
 */


interface MvvmView: BaseView {

    fun onLoginSuccess(user: User)

}