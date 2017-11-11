package com.example.llcgs.android_kotlin.kotlin.functionandlambda.function.model

import android.view.View
import android.widget.Button
import com.example.llcgs.android_kotlin.base.model.BaseModel
import com.gomejr.myf.core.kotlin.logD

/**
 * com.example.llcgs.android_kotlin.functionandlambda.function.EighteenModel
 * @author liulongchao
 * @since 2017/7/28
 */


class EighteenModel: BaseModel {

    // 命名参数
    fun reformat(str: String,
                 normalizeCase: Boolean = true,
                 upperCaseFirstLetter: Boolean = true,
                 divideByCamelHumps: Boolean = false,
                 wordSeparator: Char = ' '){

    }

    fun getJavaFun(){
        val eightModelJava = com.example.llcgs.android_kotlin.kotlin.functionandlambda.function.model.EighteenModelJava()
        // TODO Kotlin中调用Java的函数的时候，不能使用命名函数，不管是JDK中的函数，或者时Android框架的函数，都不行。
        //eightModelJava.getJavaFun(a = "", b ="", c= "")
    }

    fun getTopFunction(){

    }


}