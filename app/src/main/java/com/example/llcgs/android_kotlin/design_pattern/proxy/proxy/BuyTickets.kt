package com.example.llcgs.android_kotlin.design_pattern.proxy.proxy

/**
 * com.example.llcgs.android_kotlin.design_pattern.proxy.proxy.BuyTickets
 * @author liulongchao
 * @since 2017/12/27
 */
interface BuyTickets {

    fun buy(startingPlace:String, destination:String): String
}