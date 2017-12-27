package com.example.llcgs.android_kotlin.design_pattern.flyweight.flyweight.factory

import com.example.llcgs.android_kotlin.design_pattern.flyweight.bean.DesignPatternUser
import com.example.llcgs.android_kotlin.design_pattern.flyweight.flyweight.IUserState
import com.example.llcgs.android_kotlin.design_pattern.flyweight.flyweight.impl.UserState
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.design_pattern.flyweight.flyweight.factory.UserFactory
 * @author liulongchao
 * @since 2017/12/26
 */
class UserFactory {

    /***
     *  享元工厂
     * */
    companion object {
        val map = HashMap<String, IUserState>()
    }

    fun getUserPwd(user: DesignPatternUser): Observable<String>{
        var iUserState = map[user.name]
        return if (iUserState == null){
            iUserState = UserState(user)
            map.put(user.name, iUserState)
            Observable.just("")
        }else{
            Observable.just(iUserState.getUserPwd())
        }
    }

}