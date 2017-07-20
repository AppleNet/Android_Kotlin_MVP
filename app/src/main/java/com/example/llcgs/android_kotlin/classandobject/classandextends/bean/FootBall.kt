package com.example.llcgs.android_kotlin.classandobject.classandextends.bean

/**
 * com.example.llcgs.android_kotlin.classandobject.classandextends.bean.FootBall
 * @author liulongchao
 * @since 2017/7/20
 */


class FootBall(weight: Int) : Ball(weight) {

    override fun playBall(): String {
        return "FootBall"
    }
}