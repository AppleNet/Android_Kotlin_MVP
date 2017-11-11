package com.example.llcgs.android_kotlin.kotlin.stringandregex.bean

import com.gomejr.myf.core.kotlin.logD

/**
 * com.example.llcgs.android_kotlin.stringandregex.bean.User
 * @author liulongchao
 * @since 2017/10/18
 */


class User(val id:Int, val name:String, val address:String)


fun User.validateBeforeSave(){
    fun validate(value:String, filedName:String){
        if (value.isEmpty()){
            throw IllegalArgumentException("Can not save user ${id}: + empty $filedName")
        }else{
            id.logD()
            name.logD()
            address.logD()
        }

    }
    validate(name, address)
}