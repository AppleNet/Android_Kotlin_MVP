package com.example.llcgs.android_kotlin.design_pattern.wrapper.wrapper.impl

/**
 * com.example.llcgs.android_kotlin.design_pattern.wrapper.wrapper.impl.WeChatLogin
 * @author liulongchao
 * @since 2017/12/28
 */

/**
 *  具体包装类
 *
 * */
class WeChatLogin : UserLogin() {

    fun weChatLogin(userName: String, userPwd: String): Boolean{
        return if (userName.equals("weChat", true) && userPwd == "123456"){
            true
        }else{
            login(userName, userPwd)
        }
    }

}