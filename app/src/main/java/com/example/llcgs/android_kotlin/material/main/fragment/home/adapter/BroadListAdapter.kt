package com.example.llcgs.android_kotlin.material.main.fragment.home.adapter

import android.content.Intent
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.CardView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.login.customtabs.CustomTabActivityHelper
import com.example.llcgs.android_kotlin.material.login.customtabs.WebViewFallback
import com.example.llcgs.android_kotlin.material.main.fragment.home.bean.BroadListContent
import com.example.llcgs.android_kotlin.material.webview.MaterialWebViewActivity
import com.example.llcgs.android_kotlin.utils.CheatSheetUtils
import com.example.llcgs.android_kotlin.utils.widget.button.CardIconButton
import de.hdodenhof.circleimageview.CircleImageView

/**
 * com.example.llcgs.android_kotlin.material.main.fragment.home.adapter.BroadListAdapter
 * @author liulongchao
 * @since 2017/12/13
 */
class BroadListAdapter : BaseQuickAdapter<BroadListContent, BaseViewHolder>(R.layout.view_home_broadcast_list_recyclerview_item) {

    private var flag: Boolean = false

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

        initOnClickListener(helper, item, cardView)

    }

    private fun initOnClickListener(helper: BaseViewHolder, item: BroadListContent, cardView: CardView) {
        val like = helper.getView<CardIconButton>(R.id.like)
        like.setOnClickListener {
            if (!flag) {
                it.isActivated = true
                like.setText("1")
                flag = true
            } else {
                it.isActivated = false
                like.setText("")
                flag = false
            }
        }

        like.setOnLongClickListener {
            CheatSheetUtils.setup(it)
            true
        }

        val comment = helper.getView<CardIconButton>(R.id.comment)
        comment.setOnClickListener {
            openBroadCast?.let {
                it(item, cardView, helper.adapterPosition)
            }
        }

        val rebroadcast = helper.getView<CardIconButton>(R.id.rebroadcast)
        rebroadcast.setOnClickListener {
            // 转播
            Toast.makeText(mContext, "已转播", Toast.LENGTH_SHORT).show()
        }

        val circleImageView = helper.getView<CircleImageView>(R.id.avatar)
        circleImageView.setOnClickListener {
            // Profile
        }

        val imageView = helper.getView<ImageView>(R.id.attachment_image)
        imageView.setOnClickListener {
            // 查看大图

        }

        val des = helper.getView<TextView>(R.id.attachment_description)
        des.setOnClickListener {
            // 查看内容
            val intent = Intent(mContext, MaterialWebViewActivity::class.java)
            intent.putExtra("EXTRA_URL", item.desUrl)
            mContext.startActivity(intent)
        }


    }

    private var openBroadCast: ((item: BroadListContent, view: View, position: Int) -> Unit)? = null

    fun setOpenBroadCast(listener: ((item: BroadListContent, view: View, position: Int) -> Unit)) {
        this.openBroadCast = listener
    }
}