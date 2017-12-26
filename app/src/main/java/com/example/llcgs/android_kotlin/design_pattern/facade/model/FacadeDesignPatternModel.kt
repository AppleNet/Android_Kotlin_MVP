package com.example.llcgs.android_kotlin.design_pattern.facade.model

import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternModel
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Dough
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Fruit
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Vegetables
import com.example.llcgs.android_kotlin.design_pattern.facade.facade.impl.Facade
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.design_pattern.facade.model.FacadeDesignPatternModel
 * @author liulongchao
 * @since 2017/12/26
 */
class FacadeDesignPatternModel : BaseDesignPatternModel{

    fun calculate(fruit: Fruit, vegetables: Vegetables, dough: Dough):Observable<Any>{
        val facade = Facade(fruit, vegetables, dough)
        return Observable.just(facade.buyFruit(), facade.buyDough(), facade.buyVegetables())
    }

}