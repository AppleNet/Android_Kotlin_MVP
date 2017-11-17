package com.example.llcgs.android_kotlin.mvvm.observableobjects.viewmodel

import android.databinding.Observable
import android.view.View
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.observableobjects.model.ObservableModel

/**
 * com.example.llcgs.android_kotlin.mvvm.observableobjects.viewmodel.ObservableCollectionsViewModel
 * @author liulongchao
 * @since 2017/11/15
 */
class ObservableViewModel:BaseViewModel() {

    private var onClickListenerCallBack: ((p0: Observable?, view: View?)->Unit)? = null
    val observableModel = ObservableModel()

    init {
        observableModel.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(p0: Observable?, p1: Int) {
                onClickListenerCallBack?.let {
                    it(p0, null)
                }
            }
        })
    }

    fun buttonOnClickListener(view: View){
        // 获取editText输入的值 set给observableModel
        onClickListenerCallBack?.let {
            it(null, view)
        }
    }

    fun setOnClickListenerCallBack(listener: ((p0: Observable?, view: View?)->Unit)){
        this.onClickListenerCallBack = listener
    }
}