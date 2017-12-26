package com.example.llcgs.android_kotlin.design_pattern.list.view

import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternView

/**
 * com.example.llcgs.android_kotlin.design_pattern.list.view.DesignPatternMainView
 * @author liulongchao
 * @since 2017/12/26
 */
interface DesignPatternMainView: BaseDesignPatternView {

    fun onGetDesignPattern(list: List<String>)
}