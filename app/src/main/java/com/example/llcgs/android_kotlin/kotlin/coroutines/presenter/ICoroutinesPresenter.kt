package com.example.llcgs.android_kotlin.kotlin.coroutines.presenter;

import com.example.llcgs.android_kotlin.base.presenter.SuperPresenter
import com.example.llcgs.android_kotlin.kotlin.variable.bean.Repo

interface ICoroutinesPresenter: SuperPresenter {


    fun getUser(user: String)

}
