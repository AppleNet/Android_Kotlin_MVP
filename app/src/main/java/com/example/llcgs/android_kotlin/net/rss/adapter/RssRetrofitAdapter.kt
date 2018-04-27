package com.example.llcgs.android_kotlin.net.rss.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.net.rss.bean.retrofit.Items

/**
 * com.example.llcgs.android_kotlin.net.rss.adapter.RssRetrofitAdapter
 * @author liulongchao
 * @since 2018/4/27
 */
class RssRetrofitAdapter: BaseQuickAdapter<Items, BaseViewHolder>(R.layout.view_recyclerview_item_rss) {

    override fun convert(helper: BaseViewHolder, item: Items) {
        helper.setText(R.id.title, item.title)
        helper.setText(R.id.description, item.link)
    }
}