package com.example.llcgs.android_kotlin.design_pattern.facade.facade.impl

import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Dough
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Fruit
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Vegetables
import com.example.llcgs.android_kotlin.design_pattern.facade.facade.IFacade

/**
 * com.example.llcgs.android_kotlin.design_pattern.facade.facade.impl.Facade
 * @author liulongchao
 * @since 2017/12/26
 */
class Facade(private var fruit: Fruit = Fruit(""), private var vegetables: Vegetables = Vegetables(""), private var dough: Dough = Dough("")) : IFacade {

    override fun buyFruit(): Fruit {
        if (fruit.name.isNotEmpty()){
            return fruit.buyFruit()
        }
        return Fruit("").apply { content = "请输入您要购买的水果" }
    }

    override fun buyDough(): Dough{
        if (dough.name.isNotEmpty()){
            return dough.buyDough()
        }
        return Dough("").apply { content = "请输入您要购买的面食" }
    }

    override fun buyVegetables(): Vegetables{
        if (vegetables.name.isNotEmpty()){
            return vegetables.buyVegetables()
        }
        return Vegetables("").apply { content = "请输入您要购买的蔬菜" }
    }
}