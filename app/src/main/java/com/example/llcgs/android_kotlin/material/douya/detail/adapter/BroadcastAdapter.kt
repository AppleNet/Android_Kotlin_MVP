package com.example.llcgs.android_kotlin.material.douya.detail.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.douya.detail.bean.Comment

/**
 * com.example.llcgs.android_kotlin.material.detail.fragment.adapter.BroadcastAdapter
 * @author liulongchao
 * @since 2017/12/14
 */
class BroadcastAdapter: BaseQuickAdapter<com.example.llcgs.android_kotlin.material.douya.detail.bean.Comment, BaseViewHolder>(R.layout.view_detail_comment_item) {

    override fun convert(helper: BaseViewHolder, item: com.example.llcgs.android_kotlin.material.douya.detail.bean.Comment) {
        Glide.with(mContext).load(item.imageUrl).into(helper.getView(R.id.circleImageView))
        helper.setText(R.id.title, item.name)
        helper.setText(R.id.content, item.content)
        helper.setText(R.id.timeDate, item.timeDate)
    }
}