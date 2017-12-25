package com.example.llcgs.android_kotlin.material.detail.fragment.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.detail.fragment.bean.LikeBroadcast

/**
 * com.example.llcgs.android_kotlin.material.detail.fragment.adapter.LikeAdapter
 * @author liulongchao
 * @since 2017/12/22
 */
class LikeAdapter : BaseQuickAdapter<LikeBroadcast, BaseViewHolder>(R.layout.view_like_item) {

    override fun convert(helper: BaseViewHolder, item: LikeBroadcast) {
        val imageView = helper.getView<ImageView>(R.id.circleImageView)
        Glide.with(mContext).load(item.imageUrl).into(imageView)
        helper.setText(R.id.name, item.name)
        helper.setText(R.id.id, item.id)
    }
}