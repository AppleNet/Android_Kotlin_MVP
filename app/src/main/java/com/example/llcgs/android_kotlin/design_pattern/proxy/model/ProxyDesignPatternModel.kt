package com.example.llcgs.android_kotlin.design_pattern.proxy.model

import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternModel
import com.example.llcgs.android_kotlin.design_pattern.proxy.proxy.BuyTickets
import com.example.llcgs.android_kotlin.design_pattern.proxy.proxy.impl.ProxyFlyPigBuy
import com.example.llcgs.android_kotlin.design_pattern.proxy.proxy.impl.ProxyNestBuy
import com.example.llcgs.android_kotlin.design_pattern.proxy.proxy.impl.ProxySmartBuy
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.design_pattern.proxy.model.ProxyDesignPatternModel
 * @author liulongchao
 * @since 2017/12/27
 */
class ProxyDesignPatternModel : BaseDesignPatternModel {

    private var tickets: BuyTickets? = null
    fun buy(startingPlace: String, destination: String, which: String): Observable<String> {
        when (which) {
            "1" -> {
                tickets = ProxyFlyPigBuy()
                return Observable.just(tickets?.buy(startingPlace, destination))
            }
            "2" -> {
                tickets = ProxySmartBuy()
                return Observable.just(tickets?.buy(startingPlace, destination))
            }
            "3" -> {
                tickets = ProxyNestBuy()
                return Observable.just(tickets?.buy(startingPlace, destination))
            }
        }
        return Observable.just("暂无该代理")
    }

}