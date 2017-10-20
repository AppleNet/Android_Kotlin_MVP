package com.example.llcgs.android_kotlin.classandobject.classandextends.model

import com.example.llcgs.android_kotlin.base.model.BaseModel
import com.example.llcgs.android_kotlin.classandobject.classandextends.bean.User

/**
 * com.example.llcgs.android_kotlin.classandobject.classandextends.model.FiveModel
 * @author liulongchao
 * @since 2017/7/20
 */


class FiveModel : BaseModel{

    val user = User().apply {
        name = "Kobe"
        age = "36"
        hobby = "basketball"
    }

}