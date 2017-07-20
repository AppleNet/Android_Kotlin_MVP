package com.example.llcgs.android_kotlin.classandobject.classandextends.bean

/**
 * com.example.llcgs.android_kotlin.classandobject.classandextends.bean.FootBa
 * @author liulongchao
 * @since 2017/7/20
 */


abstract class FootBa {
    // 声明一个抽象类

    companion object{

        fun palyerFootBasketBall(name: String): String{
            return "${name} is playing basketball"
        }

    }

}