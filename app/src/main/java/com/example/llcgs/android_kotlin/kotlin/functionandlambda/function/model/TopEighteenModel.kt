package com.example.llcgs.android_kotlin.kotlin.functionandlambda.function.model

import android.view.View
import android.widget.Button
import com.example.llcgs.android_kotlin.base.model.BaseModel
import com.example.llcgs.android_kotlin.base.presenter.SuperPresenter
import com.example.llcgs.android_kotlin.base.view.BaseView
import com.example.llcgs.android_kotlin.utils.log.logD

/**
 * com.example.llcgs.android_kotlin.functionandlambda.function.model.TopEighteenModel
 * @author liulongchao
 * @since 2017/10/12
 */

// TODO 顶层函数
// TODO 翻译成Java中的静态方法
// TODO Kotlin文件中是调用不到这个这两个方法的，只有从java文件中才能调用到
fun getTopFileFun(){
    "getTopFileFun".logD()
}

fun getTopFun(){
    "getTopFun".logD()
}

// 扩展函数不能被重写
// TODO 例子

fun View.showoff(){
    "view showoff".logD()
}

fun Button.showOff(){
    "button showoff".logD()
}

// Presenter
interface TopPresenter : SuperPresenter{

}

// Model
interface TopModel: BaseModel{

}

// View
interface TopView: BaseView{

}