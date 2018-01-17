package com.example.llcgs.android_kotlin.material.animation.activity_options.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R

/**
 * com.example.llcgs.android_kotlin.material.animation.activity_options.adapter.ActivityOptionsAdapter
 * @author liulongchao
 * @since 2018/1/9
 */
class ActivityOptionsAdapter: BaseQuickAdapter<String, BaseViewHolder>(R.layout.view_item_ry_image) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.textView5, item)
    }
}