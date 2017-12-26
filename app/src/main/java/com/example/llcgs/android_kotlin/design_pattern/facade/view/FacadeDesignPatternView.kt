package com.example.llcgs.android_kotlin.design_pattern.facade.view

import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternView

/**
 * com.example.llcgs.android_kotlin.design_pattern.facade.view.FacadeDesignPatternView
 * @author liulongchao
 * @since 2017/12/26
 */
interface FacadeDesignPatternView : BaseDesignPatternView {

    fun onGetResult(any: Any)
}