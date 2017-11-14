package com.example.llcgs.android_kotlin.mvvm.resources.viewmodel

import android.databinding.ObservableBoolean
import android.view.View
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel

/**
 * com.example.llcgs.android_kotlin.mvvm.resources.viewmodel.ResourcesViewModel
 * @author liulongchao
 * @since 2017/11/13
 */


class ResourcesViewModel:BaseViewModel() {

    var firstName = "Kobe"
    var lastName = "Bryant"

    val marginFlag = ObservableBoolean(false)
    val textSizeFlag = ObservableBoolean(true)
    val textColorFlag = ObservableBoolean(false)


    fun buttonChangeColor(view: View){
        if (textColorFlag.get()){
            textColorFlag.set(false)
        }else{
            textColorFlag.set(true)
        }
    }

    fun buttonChangeSize(view: View){
        if (textSizeFlag.get()){
            textSizeFlag.set(false)
        }else{
            textSizeFlag.set(true)
        }
    }

}