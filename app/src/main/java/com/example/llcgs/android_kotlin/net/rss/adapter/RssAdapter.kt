package com.example.llcgs.android_kotlin.net.rss.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.net.rss.bean.xml.Rss

/**
 * com.example.llcgs.android_kotlin.net.rss.adapter.RssAdapter
 * @author liulongchao
 * @since 2018/4/27
 */
class RssAdapter: BaseQuickAdapter<Rss, BaseViewHolder>(R.layout.view_recyclerview_item_rss) {

    override fun convert(helper: BaseViewHolder, item: Rss) {
        helper.setText(R.id.title, item.item.title)
        helper.setText(R.id.description, item.item.guid)
    }
}