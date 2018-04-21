package com.example.llcgs.android_kotlin.net.webservice.presenter

import com.example.llcgs.android_kotlin.net.base.BaseNetWorkPresenter

/**
 * com.example.llcgs.android_kotlin.net.webservice.presenter.IWebServicePresenter
 * @author liulongchao
 * @since 2018/4/21
 */
interface IWebServicePresenter: BaseNetWorkPresenter {

    fun getWeather(cityName: String)
}