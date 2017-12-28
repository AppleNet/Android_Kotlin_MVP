package com.example.llcgs.android_kotlin.design_pattern.wrapper.wrapper.impl

/**
 * com.example.llcgs.android_kotlin.design_pattern.wrapper.wrapper.impl.AliPayLogin
 * @author liulongchao
 * @since 2017/12/28
 */
class AliPayLogin: UserLogin() {

    fun alipayLogin(userName:String, userPwd:String): Boolean{
        return if (userName.equals("alipay", true) && userPwd == "123456"){
            true
        }else{
            login(userName, userPwd)
        }
    }
}