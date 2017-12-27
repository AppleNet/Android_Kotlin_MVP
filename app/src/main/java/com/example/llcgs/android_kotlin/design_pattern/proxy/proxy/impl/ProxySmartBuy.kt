package com.example.llcgs.android_kotlin.design_pattern.proxy.proxy.impl

import com.example.llcgs.android_kotlin.design_pattern.proxy.proxy.BuyTickets

/**
 * com.example.llcgs.android_kotlin.design_pattern.proxy.proxy.impl.ProxyFlyPigBuy
 * @author liulongchao
 * @since 2017/12/27
 */
class ProxySmartBuy : BuyTickets {

    private val buy = RealBuy()
    override fun buy(startingPlace: String, destination: String)= buy.buy(startingPlace, destination)
}