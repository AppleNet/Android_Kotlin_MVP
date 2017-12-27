package com.example.llcgs.android_kotlin.design_pattern.flyweight.model

import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternModel
import com.example.llcgs.android_kotlin.design_pattern.flyweight.bean.DesignPatternUser
import com.example.llcgs.android_kotlin.design_pattern.flyweight.flyweight.factory.UserFactory
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.design_pattern.flyweight.model.FlyweightDesignPatternModel
 * @author liulongchao
 * @since 2017/12/26
 */
class FlyweightDesignPatternModel : BaseDesignPatternModel {

    private val factory = UserFactory()

    fun doLogin(user: DesignPatternUser): Observable<String> = factory.getUserPwd(user)

    fun getPwd(name:String): Observable<String> = factory.getPwd(name)
}