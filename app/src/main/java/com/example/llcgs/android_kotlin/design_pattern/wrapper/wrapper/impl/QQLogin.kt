package com.example.llcgs.android_kotlin.design_pattern.wrapper.wrapper.impl

/**
 * com.example.llcgs.android_kotlin.design_pattern.wrapper.wrapper.impl.QQLogin
 * @author liulongchao
 * @since 2017/12/28
 */
class QQLogin : UserLogin() {

    fun qqLogin(userName: String, userPwd: String): Boolean{
        return if(userName.equals("QQ", true) && userPwd == "123456"){
            true
        }else{
            login(userName, userPwd)
        }
    }

}