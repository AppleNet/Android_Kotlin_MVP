package com.example.llcgs.android_kotlin.net.rss.bean.xml

/**
 * com.example.llcgs.android_kotlin.net.rss.bean.xml.Rss
 * @author liulongchao
 * @since 2018/4/23
 */
class Rss {
    var title: String = ""
    var link: String = ""
    var language: String = ""
    var description: String = ""
    var pubDate: String = ""
    var item: RssItems = RssItems()
}