package com.example.llcgs.android_kotlin.design_pattern.proxy.proxy.impl

import com.example.llcgs.android_kotlin.design_pattern.proxy.proxy.BuyTickets

/**
 * com.example.llcgs.android_kotlin.design_pattern.proxy.proxy.impl.RealBuy
 * @author liulongchao
 * @since 2017/12/27
 */
class RealBuy: BuyTickets {

    override fun buy(startingPlace: String, destination: String): String {
        return if (startingPlace.isNotEmpty() && destination.isNotEmpty()){
             "666元"
        }else{
            "请输入起始地或目的地"
        }
    }
}