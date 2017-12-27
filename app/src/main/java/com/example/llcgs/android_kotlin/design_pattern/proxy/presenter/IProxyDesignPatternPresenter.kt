package com.example.llcgs.android_kotlin.design_pattern.proxy.presenter

import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternPresenter

/**
 * com.example.llcgs.android_kotlin.design_pattern.proxy.presenter.IProxyDesignPatternPresenter
 * @author liulongchao
 * @since 2017/12/27
 */
interface IProxyDesignPatternPresenter : BaseDesignPatternPresenter {

    fun buy(startingPlace: String, destination: String, which: String)
}