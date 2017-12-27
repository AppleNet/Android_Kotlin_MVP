package com.example.llcgs.android_kotlin.design_pattern.flyweight.flyweight.impl

import com.example.llcgs.android_kotlin.design_pattern.flyweight.bean.DesignPatternUser
import com.example.llcgs.android_kotlin.design_pattern.flyweight.flyweight.IUserState

/**
 * com.example.llcgs.android_kotlin.design_pattern.flyweight.flyweight.impl.UserState
 * @author liulongchao
 * @since 2017/12/26
 */
class UserState(private var user: DesignPatternUser): IUserState {

    override fun getUserPwd(): String = user.pwd

}