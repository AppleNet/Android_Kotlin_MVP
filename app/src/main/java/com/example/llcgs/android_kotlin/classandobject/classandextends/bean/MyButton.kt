package com.example.llcgs.android_kotlin.classandobject.classandextends.bean

import android.content.Context
import android.util.AttributeSet

/**
 * com.example.llcgs.android_kotlin.classandobject.classandextends.bean.MyButton
 * @author liulongchao
 * @since 2017/10/20
 */


class MyButton:View {

    // TODO 第一种初始化父类
    constructor(context: Context):super(context){

    }

    constructor(context: Context, attr: AttributeSet):super(context,attr){

    }

}