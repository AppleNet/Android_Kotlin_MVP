package com.example.llcgs.android_kotlin.design_pattern.simplefactory.model;

import com.example.llcgs.android_kotlin.base.model.BaseModel
import com.example.llcgs.android_kotlin.design_pattern.simplefactory.factory.LoginFactory
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.design_pattern.simplefactory.model.SimpleFactoryModel
 * @author liulongchao
 * @since 2019/2/18
 */
class SimpleFactoryModel: BaseModel {

    private val factory = LoginFactory()

    fun login(type: String): Observable<Boolean>{
        return factory.login(type)
    }

}
