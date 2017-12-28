package com.example.llcgs.android_kotlin.design_pattern.wrapper.model

import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternModel
import com.example.llcgs.android_kotlin.design_pattern.wrapper.wrapper.WrapperBean
import com.example.llcgs.android_kotlin.design_pattern.wrapper.wrapper.impl.*
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.design_pattern.wrapper.model.WrapperDesignPatternModel
 * @author liulongchao
 * @since 2017/12/27
 */
class WrapperDesignPatternModel : BaseDesignPatternModel {


    fun login(userName: String, userPwd: String, pattern:String): Observable<Boolean>{
        val wrapperBean: WrapperBean
        when(pattern){
            "weChat" ->{
                wrapperBean = WeChatLogin()
                return Observable.just(wrapperBean.weChatLogin(userName, userPwd))
            }
            "QQ" ->{
                wrapperBean = QQLogin()
                return Observable.just(wrapperBean.qqLogin(userName, userPwd))
            }
            "sina" ->{
                wrapperBean = SinaLogin()
                return Observable.just(wrapperBean.sinaLogin(userName, userPwd))
            }
            "alipay" ->{
                wrapperBean = AliPayLogin()
                return Observable.just(wrapperBean.alipayLogin(userName, userPwd))
            }
            else ->{
                wrapperBean = WrapperBean()
                return Observable.just(wrapperBean.login(userName, userPwd))
            }
        }
    }
}