package com.example.llcgs.android_kotlin.material.douya.main.fragment.home.presenter

import android.app.Activity
import android.content.Context
import android.view.View
import com.example.llcgs.android_kotlin.base.presenter.SuperPresenter
import com.example.llcgs.android_kotlin.material.base.BaseMaterialPresenter

/**
 * com.example.llcgs.android_kotlin.material.main.fragment.home.presenter.IHomeBroadcastListPresenter
 * @author liulongchao
 * @since 2017/12/13
 */
interface IHomeBroadcastListPresenter : BaseMaterialPresenter, SuperPresenter {

    fun getData()
    fun addTransitionAnimation(context: Activity, view: View)
}