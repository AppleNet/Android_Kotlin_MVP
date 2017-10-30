package com.example.llcgs.android_kotlin.mvvm.list.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * com.example.llcgs.android_kotlin.mvvm.list.model.Student
 * @author liulongchao
 * @since 2017/10/26
 */

class Student: Serializable {

    @SerializedName("gender")
    var gender: String = ""

    @SerializedName("name")
    var name: Name = Name()

    @SerializedName("location")
    var location: Location = Location()

    @SerializedName("email")
    var mail: String = ""

    @SerializedName("login")
    var userName: Login = Login()

    @SerializedName("phone")
    var phone: String = ""

    @SerializedName("cell")
    var cell: String = ""

    @SerializedName("picture")
    var picture: Picture = Picture()

    @SerializedName("fullName")
    var fullName: String = ""

    fun hasEmail() = mail.isNotEmpty()

}