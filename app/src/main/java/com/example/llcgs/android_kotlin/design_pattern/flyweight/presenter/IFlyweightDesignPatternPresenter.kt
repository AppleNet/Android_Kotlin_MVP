package com.example.llcgs.android_kotlin.design_pattern.flyweight.presenter

import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.flyweight.bean.DesignPatternUser

/**
 * com.example.llcgs.android_kotlin.design_pattern.flyweight.presenter.IFlyweightDesignPatternPresenter
 * @author liulongchao
 * @since 2017/12/26
 */
interface IFlyweightDesignPatternPresenter : BaseDesignPatternPresenter {

    fun doLogin(user: DesignPatternUser)

    fun getPwd(name: String)
}