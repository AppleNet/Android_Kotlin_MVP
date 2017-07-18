package com.example.llcgs.android_kotlin.first.model

import com.example.llcgs.android_kotlin.base.model.BaseModel
import com.example.llcgs.android_kotlin.first.bean.User

/**
 * LoginModel

 * @author liulongchao
 * *
 * @since 2017/5/18
 */


class LoginModel : BaseModel {
    fun doLogin(user: User) : Boolean?{
        return true
    }
}
