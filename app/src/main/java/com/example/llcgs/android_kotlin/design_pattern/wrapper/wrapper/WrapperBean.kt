package com.example.llcgs.android_kotlin.design_pattern.wrapper.wrapper

/**
 * com.example.llcgs.android_kotlin.design_pattern.wrapper.wrapper.WrapperBean
 * @author liulongchao
 * @since 2017/12/28
 */
open class WrapperBean {

    open fun login(userName: String, userPwd: String): Boolean =// 统一登录
            // TODO 直接业务的实际登录
            userName == "Kobe" && userPwd == "123456"
}