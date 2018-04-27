package com.example.llcgs.android_kotlin.net.rss.bean.retrofit

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

/**
 * com.example.llcgs.android_kotlin.net.rss.bean.retrofit.XmlRss
 * @author liulongchao
 * @since 2018/4/24
 */
@Root(name = "rss", strict = false)
class XmlRss {

    @get:Element(name = "channel")
    @set:Element(name = "channel")
    var channel: Channel = Channel()


    override fun toString(): String = channel.toString()
}