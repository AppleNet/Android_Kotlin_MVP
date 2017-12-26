package com.example.llcgs.android_kotlin.design_pattern.facade.bean

/**
 * com.example.llcgs.android_kotlin.design_pattern.facade.bean.Fruit
 * @author liulongchao
 * @since 2017/12/26
 */
class Fruit(var name: String) {

    var content: String = ""

    fun buyFruit(): Fruit = Fruit(name).apply { content = "$name 的价格是30元" }
}