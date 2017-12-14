package com.example.llcgs.android_kotlin.material.main.fragment.home.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * com.example.llcgs.android_kotlin.material.main.fragment.home.bean.BroadListContent
 * @author liulongchao
 * @since 2017/12/13
 */
@Parcelize
class BroadListContent: Parcelable {

    var avatar: String? = ""
    var name: String = ""
    var time: String = ""
    var content: String = ""

    var attachmentImage: String? = ""
    var attachmentTitle: String = ""
    var attachmentDes: String? = ""


}