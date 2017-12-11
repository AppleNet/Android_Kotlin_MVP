package com.example.llcgs.android_kotlin.material.login.presenter

import com.example.llcgs.android_kotlin.material.base.BaseMaterialPresenter

/**
 * com.example.llcgs.android_kotlin.material.login.presenter.IMaterialLoginPresenter
 * @author liulongchao
 * @since 2017/12/11
 */
interface IMaterialLoginPresenter : BaseMaterialPresenter {

    fun login(userName: String, userPwd: String)

}