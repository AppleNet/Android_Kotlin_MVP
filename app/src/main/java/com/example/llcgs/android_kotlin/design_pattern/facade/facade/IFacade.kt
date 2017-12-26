package com.example.llcgs.android_kotlin.design_pattern.facade.facade

import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Dough
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Fruit
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Vegetables

/**
 * com.example.llcgs.android_kotlin.design_pattern.facade.facade.IFacade
 * @author liulongchao
 * @since 2017/12/26
 */
interface IFacade {

    fun buyFruit(): Fruit
    fun buyDough(): Dough
    fun buyVegetables(): Vegetables

}