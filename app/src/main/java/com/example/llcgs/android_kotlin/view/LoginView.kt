package com.example.llcgs.android_kotlin.view

/**
 * com.example.llcgs.android_kotlin.view.LoginView
 * @author liulongchao
 * @since 2017/5/18
 */


interface LoginView {

    fun showLoadingDialog()
    fun dismissLoadingDialog()
    fun doLoginSuccess()
    fun doLoginFail()
}