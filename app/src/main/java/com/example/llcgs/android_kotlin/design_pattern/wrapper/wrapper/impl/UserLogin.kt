package com.example.llcgs.android_kotlin.design_pattern.wrapper.wrapper.impl

import com.example.llcgs.android_kotlin.design_pattern.wrapper.wrapper.WrapperBean

/**
 * com.example.llcgs.android_kotlin.design_pattern.wrapper.wrapper.impl.UserLogin
 * @author liulongchao
 * @since 2017/12/28
 */
/**
 *  包装类
 *
 * */
open class UserLogin: WrapperBean() {

    override fun login(userName: String, userPwd: String): Boolean =// 处理统一登录
            login(userName, userPwd)
}