package com.example.llcgs.android_kotlin.classandobject.enumclass.bean

/**
 * com.example.llcgs.android_kotlin.classandobject.enumclass.bean.ProtocolStates
 * @author liulongchao
 * @since 2017/7/25
 */

enum class ProtocolState {

    // 生成匿名内部类
    WATTING{
        override fun singal() = TALKING
    },

    // 生成匿名内部类
    TALKING{
        override fun singal() = WATTING
    };

    abstract fun singal(): ProtocolState

    // 当枚举中声明抽象方法的时候， 声明的匿名内部类必须实现抽象方法
}