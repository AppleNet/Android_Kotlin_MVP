package com.example.llcgs.android_kotlin.design_pattern.flyweight.view

import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternView
import com.example.llcgs.android_kotlin.design_pattern.flyweight.bean.DesignPatternUser

/**
 * com.example.llcgs.android_kotlin.design_pattern.flyweight.view.FlyweightDesignPatternView
 * @author liulongchao
 * @since 2017/12/26
 */
interface FlyweightDesignPatternView : BaseDesignPatternView {

    fun onGetPwd(string: String)

    fun onGetLogin(boolean: Boolean)
}