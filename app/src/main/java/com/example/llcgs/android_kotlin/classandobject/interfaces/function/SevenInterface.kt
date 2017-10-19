package com.example.llcgs.android_kotlin.classandobject.interfaces.function

import com.example.llcgs.android_kotlin.classandobject.interfaces.SevenInterface

/**
 * com.example.llcgs.android_kotlin.classandobject.interfaces.function.SevenInterface
 * @author liulongchao
 * @since 2017/10/18
 */


// 点击的时候 将一个对象或者接口返回的高级写法
private var onSevenInterfaceOne: ((sevenInterface: SevenInterface)->Unit)? = null

fun setOnSevenInterfaceOne(listener: (sevenInterface: SevenInterface)->Unit){
    onSevenInterfaceOne = listener
}
