package com.example.llcgs.android_kotlin.material.animation.activity_options.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R
import de.hdodenhof.circleimageview.CircleImageView

/**
 * com.example.llcgs.android_kotlin.material.animation.activity_options.adapter.ActivityOptionsAdapter
 * @author liulongchao
 * @since 2018/1/9
 */
class ActivityOptionsAdapter: BaseQuickAdapter<String, BaseViewHolder>(R.layout.view_item_ry_image) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.getView<TextView>(R.id.textView5).transitionName = "textView5"
        helper.getView<CircleImageView>(R.id.circleImageView).transitionName = "circleImageView"
        helper.setText(R.id.textView5, item)
    }
}