package com.example.llcgs.android_kotlin.mvvm.list.model

import com.google.gson.annotations.SerializedName

/**
 * com.example.llcgs.android_kotlin.mvvm.list.model.StudentResponse
 * @author liulongchao
 * @since 2017/10/26
 */


class StudentResponse {

    @SerializedName("results")
    var peopleList: List<Student> = ArrayList()

}