package com.example.llcgs.android_kotlin.architecture_components.lifecycle.presenter

import com.example.llcgs.android_kotlin.architecture_components.base.presenter.BaseArchPresenter
import com.example.llcgs.android_kotlin.architecture_components.lifecycle.bean.Login

/**
 * com.example.llcgs.android_kotlin.architecture_components.lifecycle.presenter.ILifecyclePresenter
 * @author liulongchao
 * @since 2017/11/27
 */
interface ILifecyclePresenter:BaseArchPresenter {

    fun doLogin(login: Login)
}