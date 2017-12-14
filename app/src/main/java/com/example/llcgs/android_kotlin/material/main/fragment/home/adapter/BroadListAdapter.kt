package com.example.llcgs.android_kotlin.material.main.fragment.home.adapter

import android.support.v4.view.ViewCompat
import android.support.v7.widget.CardView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.main.fragment.home.bean.BroadListContent
import de.hdodenhof.circleimageview.CircleImageView

/**
 * com.example.llcgs.android_kotlin.material.main.fragment.home.adapter.BroadListAdapter
 * @author liulongchao
 * @since 2017/12/13
 */
class BroadListAdapter : BaseQuickAdapter<BroadListContent, BaseViewHolder>(R.layout.view_home_broadcast_list_recyclerview_item) {

    override fun convert(helper: BaseViewHolder, item: BroadListContent) {

        // helper.
        val circleImageView = helper.getView<CircleImageView>(R.id.avatar)
        Glide.with(mContext).load(item.avatar).into(circleImageView)
        helper.setText(R.id.name, item.name)
        helper.setText(R.id.time_action, item.time)
        helper.setText(R.id.text, item.content)

        // helper.
        val imageView = helper.getView<ImageView>(R.id.attachment_image)
        Glide.with(mContext).load(item.attachmentImage).into(imageView)
        helper.setText(R.id.attachment_title, item.attachmentTitle)
        helper.setText(R.id.attachment_description, item.attachmentDes)

        val cardView = helper.getView<CardView>(R.id.card)
        cardView.setOnClickListener {
            openBroadCast?.let {
                it(item, cardView, helper.adapterPosition)
            }
        }
        ViewCompat.setTransitionName(cardView, "broadcast-${helper.adapterPosition}")

    }

    private var openBroadCast: ((item: BroadListContent, view: View, position: Int) -> Unit)? = null

    fun setOpenBroadCast(listener: ((item: BroadListContent, view: View, position: Int) -> Unit)){
        this.openBroadCast = listener
    }
}