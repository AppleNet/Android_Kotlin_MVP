package com.example.llcgs.android_kotlin.design_pattern.proxy.view

import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternView

/**
 * com.example.llcgs.android_kotlin.design_pattern.proxy.view.ProxyDesignPatternView
 * @author liulongchao
 * @since 2017/12/27
 */
interface ProxyDesignPatternView : BaseDesignPatternView {

    fun onGetPrice(string: String)
}