package com.example.llcgs.android_kotlin.architecture_components.menu.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R

/**
 * com.example.llcgs.android_kotlin.architecture_components.menu.adapter.MenuArchAdapter
 * @author liulongchao
 * @since 2017/11/29
 */
class MenuArchAdapter:BaseQuickAdapter<String, BaseViewHolder>(R.layout.view_item_ry) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.textView4, item)
    }
}