package com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R

/**
 * com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.adapter.RecyclerViewFragmentAdapter
 * @author liulongchao
 * @since 2017/12/1
 */
class RecyclerViewFragmentAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.view_item_ry) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.textView4, item)
    }
}