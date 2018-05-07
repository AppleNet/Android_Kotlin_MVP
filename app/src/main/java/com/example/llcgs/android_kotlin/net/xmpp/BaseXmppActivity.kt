package com.example.llcgs.android_kotlin.net.xmpp

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.example.llcgs.android_kotlin.net.base.BaseNetWorkActivity
import com.example.llcgs.android_kotlin.net.base.BaseNetWorkPresenter
import com.example.llcgs.android_kotlin.net.xmpp.presenter.impl.XmppPresenter
import com.example.llcgs.android_kotlin.net.xmpp.view.XmppView
import org.jivesoftware.smack.RosterEntry
import org.jivesoftware.smack.packet.Packet

/**
 * com.example.llcgs.android_kotlin.net.xmpp.BaseXmppActivity
 * @author liulongchao
 * @since 2018/5/7
 */
abstract class BaseXmppActivity<P : BaseNetWorkPresenter> : BaseNetWorkActivity<P>(), XmppView{

    override fun initData() {
        (mPresenter as XmppPresenter).addSubscriptionListener()
    }

    override fun onGetRequest(packet: Packet) {
        val name = packet.from.substring(0, packet.from.indexOf("@"))
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("好友添加申请")
        dialog.setMessage("同意接受${packet.from}的好友请求")
        dialog.setPositiveButton("确定") { _, _ ->
            (mPresenter as XmppPresenter).acceptFriend(name)
        }
        dialog.setNegativeButton("取消"){ dialog, _ ->
            dialog.dismiss()
        }
        dialog.create().show()
    }

    override fun onRegisterSuccess(boolean: Boolean) {

    }

    override fun onAddSuccess(boolean: Boolean) {

    }

    override fun onGetFriendList(collection: Collection<RosterEntry>) {

    }

    override fun onLoginSuccess(boolean: Boolean) {

    }
}