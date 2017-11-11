package com.example.llcgs.android_kotlin.replugin.plugin.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R

/**
 * com.example.llcgs.android_kotlin.plugin.adapter.PluginAdapter
 * @author liulongchao
 * @since 2017/8/29
 */


class PluginAdapter: BaseQuickAdapter<String, BaseViewHolder>(R.layout.view_item_ry) {
    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setText(R.id.textView4, item)
    }
}