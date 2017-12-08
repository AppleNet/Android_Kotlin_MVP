package com.example.llcgs.android_kotlin.architecture_components.room.menu.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.room.menu.db.MenuBean

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.menu.adapter.RoomMenuAdapter
 * @author liulongchao
 * @since 2017/12/8
 */
class RoomMenuAdapter: BaseQuickAdapter<MenuBean, BaseViewHolder>(R.layout.view_item_ry) {

    override fun convert(helper: BaseViewHolder?, item: MenuBean?) {
        helper?.setText(R.id.textView4, item?.content)
    }
}