package com.example.llcgs.android_kotlin.design_pattern.wrapper.wrapper.impl

/**
 * com.example.llcgs.android_kotlin.design_pattern.wrapper.wrapper.impl.SinaLogin
 * @author liulongchao
 * @since 2017/12/28
 */
class SinaLogin: UserLogin() {

    fun sinaLogin(userName:String, userPwd:String): Boolean{
        return if (userName.equals("sina", true) && userPwd == "123456"){
            true
        }else{
            login(userName, userPwd)
        }
    }
}