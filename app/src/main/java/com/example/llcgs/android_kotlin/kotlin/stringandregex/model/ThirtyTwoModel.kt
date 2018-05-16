package com.example.llcgs.android_kotlin.kotlin.stringandregex.model

import com.example.llcgs.android_kotlin.base.model.BaseModel
import com.example.llcgs.android_kotlin.kotlin.stringandregex.bean.User
import com.example.llcgs.android_kotlin.kotlin.stringandregex.bean.validateBeforeSave
import com.example.llcgs.android_kotlin.utils.log.logD

/**
 * com.example.llcgs.android_kotlin.stringandregex.model.ThirtyTwoModel
 * @author liulongchao
 * @since 2017/10/17
 */


class ThirtyTwoModel: BaseModel {

    // 编写局部函数
    fun saveUser(user: User){
        fun validate(value:String, fileName:String){
            if (value.isEmpty()){
                // user.id 局部函数访问外部函数参数
                throw IllegalArgumentException("Can not save user ${user.id}: + empty $fileName")
            }else{
                user.id.logD()
                user.name.logD()
                user.address.logD()
            }
        }
        validate(user.name, "Name")
        validate(user.address, "Address")
    }

    fun save(user: User){
        user.validateBeforeSave()

    }

}