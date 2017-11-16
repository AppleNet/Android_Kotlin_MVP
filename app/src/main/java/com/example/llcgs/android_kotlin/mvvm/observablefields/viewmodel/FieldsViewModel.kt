package com.example.llcgs.android_kotlin.mvvm.observablefields.viewmodel

import android.view.View
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.observablefields.model.FieldsModel
import com.example.llcgs.android_kotlin.mvvm.observablefields.model.Person

/**
 * com.example.llcgs.android_kotlin.mvvm.observablefields.viewmodel.FieldsViewModel
 * @author liulongchao
 * @since 2017/11/15
 */
class FieldsViewModel:BaseViewModel() {

    //
    val fieldsModel = FieldsModel()

    fun buttonClick(view:View){
        //
        fieldsModel.field.set("NBA")
        fieldsModel.BooleanFlag.set(true)
        fieldsModel.ByteFlag.set(1)
        fieldsModel.CharFlag.set('A')
        fieldsModel.IntFlag.set(1)
        fieldsModel.ShortFlag.set(1.toShort())
        fieldsModel.LongFlag.set(1.toLong())
        fieldsModel.FloatFlag.set(1.toFloat())
        fieldsModel.DoubleFlag.set(1.1)
        fieldsModel.parcel.set(Person().apply { name="Kobe"; age="37"; hobby="basketball"; height="197" })
    }

}