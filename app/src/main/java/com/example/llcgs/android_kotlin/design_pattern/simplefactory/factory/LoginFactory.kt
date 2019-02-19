package com.example.llcgs.android_kotlin.design_pattern.simplefactory.factory;

import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.design_pattern.simplefactory.factory.LoginFactory
 * @author liulongchao
 * @since 2019/2/18
 */
class LoginFactory {

    private lateinit var  login: ILogin

    fun login(type: String): Observable<Boolean>{
        when(type.toLowerCase()){
            "wechat" -> {
                login = WeChatLogin()
            }
            "qq" -> {
                login = QQLogin()
            }
            "sina" ->{
                login = SinaLogin()
            }
        }
        return Observable.just(login.login(type))
    }
}
