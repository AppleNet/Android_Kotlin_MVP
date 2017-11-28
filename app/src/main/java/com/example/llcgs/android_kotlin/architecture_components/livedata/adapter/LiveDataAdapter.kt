package com.example.llcgs.android_kotlin.architecture_components.livedata.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.livedata.bean.LiveDataBean

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.adapter.LiveDataAdapter
 * @author liulongchao
 * @since 2017/11/28
 */
class LiveDataAdapter : BaseQuickAdapter<LiveDataBean, BaseViewHolder>(R.layout.view_item_ry) {

    override fun convert(helper: BaseViewHolder, item: LiveDataBean) {
        helper.setText(R.id.textView4, item.content)
    }
}