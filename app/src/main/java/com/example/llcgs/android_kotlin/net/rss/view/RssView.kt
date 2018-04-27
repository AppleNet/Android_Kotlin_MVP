package com.example.llcgs.android_kotlin.net.rss.view

import com.example.llcgs.android_kotlin.net.base.BaseNetWorkView
import com.example.llcgs.android_kotlin.net.rss.bean.retrofit.XmlRss
import com.example.llcgs.android_kotlin.net.rss.bean.xml.Rss

/**
 * com.example.llcgs.android_kotlin.net.rss.view.RssView
 * @author liulongchao
 * @since 2018/4/23
 */
interface RssView: BaseNetWorkView {

    fun onGetXmlRss(xmlRss: ArrayList<Rss>)

    fun onGetXmlRssByRetrofit(xmlRss: XmlRss)
}