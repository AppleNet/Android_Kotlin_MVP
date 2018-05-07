package com.example.llcgs.android_kotlin.net.xmpp.view

import com.example.llcgs.android_kotlin.net.base.BaseNetWorkView
import org.jivesoftware.smack.RosterEntry
import org.jivesoftware.smack.packet.Packet

/**
 * com.example.llcgs.android_kotlin.net.xmpp.view.XmppView
 * @author liulongchao
 * @since 2018/5/4
 */
interface XmppView: BaseNetWorkView {
    fun onRegisterSuccess(boolean: Boolean)
    fun onLoginSuccess(boolean: Boolean)
    fun onAddSuccess(boolean: Boolean)
    fun onGetFriendList(collection: Collection<RosterEntry>)
    fun onGetRequest(packet: Packet)

}