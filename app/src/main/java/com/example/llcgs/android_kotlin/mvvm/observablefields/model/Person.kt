package com.example.llcgs.android_kotlin.mvvm.observablefields.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * com.example.llcgs.android_kotlin.mvvm.observablefields.model.Person
 * @author liulongchao
 * @since 2017/11/15
 */
@Parcelize
class Person : Parcelable {

    var name : String = ""
    var age : String = ""
    var height : String = ""
    var hobby:String = ""

}