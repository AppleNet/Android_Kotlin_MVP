package com.example.llcgs.android_kotlin.material.login.model

import com.example.llcgs.android_kotlin.material.base.BaseMaterialModel
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.material.login.model.MaterialLoginModel
 * @author liulongchao
 * @since 2017/12/11
 */
class MaterialLoginModel : BaseMaterialModel {

    fun login(userName: String, userPwd: String): Observable<Boolean> {
        if (userName.equals( "Kobe", true) && userPwd.equals("123456", true)){
            return Observable.just(true)
        }
        return Observable.just(false)
    }

}