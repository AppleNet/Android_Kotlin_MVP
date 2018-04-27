package com.example.llcgs.android_kotlin.net.rss.presenter

import com.example.llcgs.android_kotlin.net.base.BaseNetWorkPresenter

/**
 * com.example.llcgs.android_kotlin.net.rss.presenter.IRssPresenter
 * @author liulongchao
 * @since 2018/4/23
 */
interface IRssPresenter: BaseNetWorkPresenter {

    fun getRssContent()

    fun getRssContentByRetrofit()
}