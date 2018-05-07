package com.example.llcgs.android_kotlin.net.xmpp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R
import org.jivesoftware.smack.RosterEntry

/**
 * com.example.llcgs.android_kotlin.net.xmpp.adapter.XmppListAdapter
 * @author liulongchao
 * @since 2018/5/7
 */
class XmppListAdapter: BaseQuickAdapter<RosterEntry, BaseViewHolder>(R.layout.view_item_ry) {

    override fun convert(helper: BaseViewHolder, item: RosterEntry) {
        helper.setText(R.id.textView4, item.user)
    }
}