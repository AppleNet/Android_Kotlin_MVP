package com.example.llcgs.android_kotlin.design_pattern.facade.bean

/**
 * com.example.llcgs.android_kotlin.design_pattern.facade.bean.Fruit
 * @author liulongchao
 * @since 2017/12/26
 */
class Vegetables(var name:String){

    var content:String = ""

    fun buyVegetables(): Vegetables = Vegetables(name).apply { content = "$name 的价格是15元" }
}