package com.example.llcgs.android_kotlin.net.webservice.view

import com.example.llcgs.android_kotlin.net.base.BaseNetWorkView

/**
 * com.example.llcgs.android_kotlin.net.webservice.view.WebServiceView
 * @author liulongchao
 * @since 2018/4/21
 */
interface WebServiceView: BaseNetWorkView {

    fun onShowWeather(string: String)
}