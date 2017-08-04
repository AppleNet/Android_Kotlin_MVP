package com.example.llcgs.android_kotlin.other.structdeclarations.bean

/**
 * com.example.llcgs.android_kotlin.other.structdeclarations.bean.User
 * @author liulongchao
 * @since 2017/8/4
 */


class User(var name:String, var age: String){

    operator fun component1(): String{
        return this.name
    }

    operator fun component2(): String{
        return this.age
    }

}