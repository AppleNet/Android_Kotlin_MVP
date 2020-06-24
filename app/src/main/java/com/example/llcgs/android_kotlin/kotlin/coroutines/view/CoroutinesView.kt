package com.example.llcgs.android_kotlin.kotlin.coroutines.view;

import com.example.llcgs.android_kotlin.base.view.BaseView
import com.example.llcgs.android_kotlin.kotlin.variable.bean.Repo

interface CoroutinesView: BaseView {


    fun onGetUser(users: List<Repo>)

}
