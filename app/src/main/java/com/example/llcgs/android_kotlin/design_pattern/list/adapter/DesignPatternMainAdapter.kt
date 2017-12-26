package com.example.llcgs.android_kotlin.design_pattern.list.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R

/**
 * com.example.llcgs.android_kotlin.design_pattern.list.adapter.DesignPatternMainAdapter
 * @author liulongchao
 * @since 2017/12/26
 */
class DesignPatternMainAdapter: BaseQuickAdapter<String, BaseViewHolder>(R.layout.view_item_ry) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.textView4, item)
    }
}