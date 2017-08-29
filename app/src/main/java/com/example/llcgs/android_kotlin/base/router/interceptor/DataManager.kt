package com.example.llcgs.android_kotlin.base.router.interceptor

/**
 * com.example.llcgs.android_kotlin.base.router.interceptor.DataManager
 * @author liulongchao
 * @since 2017/8/29
 */


enum class DataManager {
    INSTANCE;

    private var isLogin:Boolean = false

    fun isLogin() = isLogin

    fun setLogin(login:Boolean) {
        this.isLogin = login
    }
}