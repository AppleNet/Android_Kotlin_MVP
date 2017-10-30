package com.example.llcgs.android_kotlin.mvvm.list.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * com.example.llcgs.android_kotlin.mvvm.list.model.Name
 * @author liulongchao
 * @since 2017/10/26
 */


class Name:Serializable {
    @SerializedName("title")
    var title: String = ""

    @SerializedName("first")
    var firts: String = ""

    @SerializedName("last")
    var last: String = ""
}