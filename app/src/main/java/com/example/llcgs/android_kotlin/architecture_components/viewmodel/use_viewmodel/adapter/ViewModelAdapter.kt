package com.example.llcgs.android_kotlin.architecture_components.viewmodel.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R

/**
 * com.example.llcgs.android_kotlin.architecture_components.viewmodel.use_viewmodel.ViewModelAdapter
 * @author liulongchao
 * @since 2017/11/30
 */
class ViewModelAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.view_item_ry_horizontal) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.textView4, item)
    }
}