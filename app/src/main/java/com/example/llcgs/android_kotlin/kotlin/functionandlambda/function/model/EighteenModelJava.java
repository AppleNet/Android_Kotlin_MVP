package com.example.llcgs.android_kotlin.kotlin.functionandlambda.function.model;

import android.util.Log;

import com.example.llcgs.android_kotlin.kotlin.functionandlambda.function.presenter.impl.EighteenPresenterKt;


/**
 * com.example.llcgs.android_kotlin.functionandlambda.function.model.EighteenModelJava
 *
 * @author liulongchao
 * @since 2017/10/12
 */


public class EighteenModelJava implements TopPresenter {

    private void getKtFun(){
        EighteenModel eighteenModel = new EighteenModel();
        // TODO Java中调用Kotlin中的函数的时候 不能使用命名的方式
        // 同理 kotlin中调用Java的函数的时候，也不能采用命名函数
        // eighteenModel.reformat(str = "");
        EighteenPresenterKt.getModel(eighteenModel);
    }

    public void getJavaFun(String a, String b, String c){
        Log.d("MainActivity", "a: " + a);
    }

    public void getTopFunctions(){
        TopEighteenModelKt.getTopFileFun();
        TopEighteenModelKt.getTopFun();
    }

}
