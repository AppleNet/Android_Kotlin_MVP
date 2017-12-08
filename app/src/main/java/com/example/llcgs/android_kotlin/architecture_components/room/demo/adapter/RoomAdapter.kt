package com.example.llcgs.android_kotlin.architecture_components.room.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.room.demo.db.bean.Notice

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.demo.RoomAdapter
 * @author liulongchao
 * @since 2017/12/6
 */
class RoomAdapter : BaseQuickAdapter<Notice, BaseViewHolder>(R.layout.view_item_ry) {

    override fun convert(helper: BaseViewHolder?, item: Notice?) {
        helper?.setText(R.id.textView4, item?.firstName + "-" + item?.lastName)
    }
}