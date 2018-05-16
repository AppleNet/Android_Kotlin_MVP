package com.example.llcgs.android_kotlin.kotlin.classandobject.enumclass.bean

import com.example.llcgs.android_kotlin.utils.log.logD

/**
 * com.example.llcgs.android_kotlin.classandobject.enumclass.bean.User
 * @author liulongchao
 * @since 2017/7/25
 */


enum class User(user: String) {

    // 声明一个枚举类
    //每个枚举常量都是⼀个对象，用逗号分隔
    KOBE("kobe"),
    JAMES("james"),
    MCGRADY("McGrady"),
    JODN("jodn"),
    BOSH("bosh"),
    DURANT("durant"),
    NASH("nash"),
    WADE("wade"),
    ANTHDOY("anthdoy");

    var user: String? = null
    init {
        this.user = user
    }

    constructor(user: String, age: String): this(user){
        this.user = user
    }

    inline fun <reified T : Enum<T>> printAllValues() {
        enumValues<T>().joinToString { it.name }.logD()
    }

}