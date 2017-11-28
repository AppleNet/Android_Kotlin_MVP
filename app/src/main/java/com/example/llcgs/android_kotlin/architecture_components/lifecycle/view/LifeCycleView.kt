package com.example.llcgs.android_kotlin.architecture_components.lifecycle.view

import com.example.llcgs.android_kotlin.architecture_components.base.view.BaseArchView

/**
 * com.example.llcgs.android_kotlin.architecture_components.lifecycle.view.LifeCycleView
 * @author liulongchao
 * @since 2017/11/27
 */
interface LifeCycleView : BaseArchView {

    fun onLoginSuccess(success: Boolean)
}