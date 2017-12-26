package com.example.llcgs.android_kotlin.design_pattern.facade.presenter

import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Dough
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Fruit
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Vegetables

/**
 * com.example.llcgs.android_kotlin.design_pattern.facade.presenter.IFacadeDesignPatternPresenter
 * @author liulongchao
 * @since 2017/12/26
 */
interface IFacadeDesignPatternPresenter : BaseDesignPatternPresenter {

    fun calculate(fruit: Fruit, vegetables: Vegetables, dough: Dough)
}