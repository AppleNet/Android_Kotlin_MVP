package com.example.llcgs.android_kotlin.net.rss.bean.retrofit

import org.simpleframework.xml.Element
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

/**
 * com.example.llcgs.android_kotlin.net.rss.bean.retrofit.Items
 * @author liulongchao
 * @since 2018/4/24
 */
@Root(name = "item", strict = false)
class Items {

    @get:Element(name = "title", required = false)
    @set:Element(name = "title", required = false)
    @Path("item")
    var title: String = ""

    @get:Element(name = "link", required = false)
    @set:Element(name = "link", required = false)
    @Path("item")
    var link: String = ""

    @get:Element(name = "description", required = false)
    @set:Element(name = "description", required = false)
    @Path("item")
    var description: String = ""

    @get:Element(name = "copyright", required = false)
    @set:Element(name = "copyright", required = false)
    @Path("item")
    var copyright: String = ""

    @get:Element(name = "pubDate", required = false)
    @set:Element(name = "pubDate", required = false)
    @Path("item")
    var pubDate: String = ""

    @get:Element(name = "comments", required = false)
    @set:Element(name = "comments", required = false)
    @Path("item")
    var comments: String = ""


}