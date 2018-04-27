package com.example.llcgs.android_kotlin.net.rss.bean.retrofit

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

/**
 * com.example.llcgs.android_kotlin.net.rss.bean.retrofit.Channel
 * @author liulongchao
 * @since 2018/4/24
 */
@Root(name = "channel", strict = false)
class Channel {

    @get:Element(name = "title")
    @set:Element(name = "title")
    @Path("channel")
    var title: String = ""

    @get:Element(name = "description")
    @set:Element(name = "description")
    @Path("channel")
    var description: String = ""

    @get:Element(name = "link")
    @set:Element(name = "link")
    @Path("channel")
    var link: String = ""

    @get:ElementList(inline = true, name = "item")
    @set:ElementList(inline = true, name = "item")
    @Path("channel")
    var itemList: List<Items> = ArrayList()

    override fun toString(): String =
        "title: $title, description: $description, link: $link"
}