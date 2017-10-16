package com.example.llcgs.android_kotlin.functionandlambda.function.presenter.impl

import com.example.llcgs.android_kotlin.base.presenter.BasePresenter
import com.example.llcgs.android_kotlin.classandobject.modifier.presenter.IEightPresenter
import com.example.llcgs.android_kotlin.functionandlambda.function.model.EighteenModel
import com.example.llcgs.android_kotlin.functionandlambda.function.view.EighteenView
import com.gomejr.myf.core.kotlin.logD

/**
 * com.example.llcgs.android_kotlin.functionandlambda.function.EighteenPresenter
 * @author liulongchao
 * @since 2017/7/28
 */


class EighteenPresenter: BasePresenter<EighteenView>(), IEightPresenter {


    private var privcyVar = "";

    val eighteenModel = EighteenModel()

    fun getTopsFun(){
        eighteenModel.getJavaFun()
        eighteenModel.getModel()
    }

}


// TODO 给EighteenModel 添加拓展函数
fun EighteenModel.getModel(){
    this.javaClass.simpleName.logD()
}

fun BasePresenter<EighteenView>.getPresenter(){
    // privcyVar 在扩展函数中不能访问
    this.javaClass.simpleName.logD()
}