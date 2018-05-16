package com.example.llcgs.android_kotlin.net.xmpp.model

import android.os.Handler
import android.os.Looper
import com.example.llcgs.android_kotlin.base.rx.RxBus
import com.example.llcgs.android_kotlin.net.base.BaseNetWorkModel
import com.example.llcgs.android_kotlin.net.xmpp.bean.XmppUser
import com.example.llcgs.android_kotlin.net.xmpp.helper.XmppConnection
import com.example.llcgs.android_kotlin.utils.log.logD
import io.reactivex.Observable
import org.jivesoftware.smack.PacketListener
import org.jivesoftware.smack.RosterEntry
import org.jivesoftware.smack.SmackConfiguration
import org.jivesoftware.smack.packet.IQ
import org.jivesoftware.smack.packet.Registration
import org.jivesoftware.smack.filter.PacketTypeFilter
import org.jivesoftware.smack.filter.PacketIDFilter
import org.jivesoftware.smack.filter.AndFilter
import org.jivesoftware.smack.filter.PacketFilter
import org.jivesoftware.smack.packet.Packet
import org.jivesoftware.smack.packet.Presence




/**
 * com.example.llcgs.android_kotlin.net.xmpp.model.XmppModel
 * @author liulongchao
 * @since 2018/5/4
 */
class XmppModel: BaseNetWorkModel {

    /**
     *  asmack提供了Presence的一个类
    ​   Presence介绍：
    ​   Presence是继承自XMPP的基类Packet信息包，Presence主要有两个用途：
    ​   ① 告诉服务器所有客户端当前所处的状态，
    ​   ② 发出添加/删除好友请求；每个Presence信息包都有一个类型属性Presence.Type 如下：
    ​   available: 表示处于在线状态
    ​   unavailable: 表示处于离线状态
    ​   subscribe: 表示发出添加好友的申请
    ​   unsubscribe: 表示发出删除好友的申请
    ​   unsubscribed: 表示拒绝添加对方为好友
    ​   error: 表示presence信息报中包含了一个错误消息
     *
     * */
    fun login(userName: String, userPwd: String): Observable<Boolean>{
        return Observable.just(XmppUser(userName, userPwd))
            .map {
                XmppConnection.getConnection().login(it.userName, it.userPwd)
                val presence = Presence(Presence.Type.available)
                XmppConnection.getConnection().sendPacket(presence)
                true
            }
    }

    fun logOut(): Observable<Boolean>{
        return Observable.just("")
            .map {
                val presence = Presence(Presence.Type.unavailable)
                XmppConnection.getConnection().disconnect(presence)
                true
            }
    }

    fun addFriend(name: String): Observable<Boolean>{
        return Observable.just(name)
            .map {
                // 两种添加方式
//                val subscription: Packet = Presence(Presence.Type.subscribe)
//                subscription.to = "$name@gomejr"
//                XmppConnection.getConnection().sendPacket(subscription)
                val roster = XmppConnection.getConnection().roster
                // 添加联系人 参数分别为：用户名 昵称 分组
                roster.createEntry("$name@gomejr", null, arrayOf("friends"))
                true
            }
    }

    fun registerUser(userName: String, userPwd: String, userEmail: String, userAlias: String): Observable<Boolean>{
        return Observable.just(XmppUser(userName, userPwd))
            .map {
                val reg = Registration()
                // 设置类型
                reg.type = IQ.Type.SET
                // 发送到服务器
                reg.to = XmppConnection.getConnection().serviceName
                // 设置用户名
                reg.setUsername(it.userName)
                // 设置密码
                reg.setPassword(it.userPwd)
                // 设置其余属性 不填可能会报500异常 连接不到服务器 asmack一个Bug
                // 设置昵称（其余属性）
                reg.addAttribute("name", userAlias)
                // 设置邮箱（其余属性）
                reg.addAttribute("email", userEmail)
                // 设置android端注册
                reg.addAttribute("android", "geolo_createUser_android")
                // 创建包过滤器
                val filter = AndFilter(PacketIDFilter(reg.packetID), PacketTypeFilter(IQ::class.java))
                // 创建包收集器
                val collector = XmppConnection.getConnection().createPacketCollector(filter)
                // 发送包
                XmppConnection.getConnection().sendPacket(reg)
                // 获取返回信息
                val result = collector.nextResult(SmackConfiguration.getPacketReplyTimeout().toLong()) as IQ?
                // 停止请求results（是否成功的结果）
                collector.cancel()
                when {
                    result == null -> //无返回，连接不到服务器
                        false
                    result.type == IQ.Type.ERROR -> //错误状态
                        !result.error.toString().equals("conflict(409)", true)
                    else ->
                        result.type == IQ.Type.RESULT
                }
            }
    }

    fun getFriendList(): Observable<Collection<RosterEntry>>{
        return Observable.just("")
            .map {
                val roster = XmppConnection.getConnection().roster
                roster.entries
            }
    }

    fun addSubscriptionListener(): Observable<Boolean>{
        //创建包过滤器
        val filter = PacketFilter { packet ->
            if (packet is Presence){
            "add callback packet.type: ${packet.type}".logD()
            if (packet.type == Presence.Type.subscribe) {
                // 是好友邀请状态就返回true 向下执行
                return@PacketFilter true
            }
        }
            false
        }

        val subscriptionPacketListener = PacketListener {
            "add callback packet: ${it.from}".logD()
            Handler(Looper.getMainLooper()).post {
                RxBus.getInstance().post(it)
            }
        }

        return Observable.just("")
            .map {
                //开启监听
                XmppConnection.getConnection().addPacketListener(subscriptionPacketListener, filter)
                true
            }
    }

    fun acceptFriend(name: String): Observable<Boolean>{
        return Observable.just("")
            .map {
                val roster = XmppConnection.getConnection().roster
                // 添加联系人 参数分别为：用户名 昵称 分组
                roster.createEntry("$name@gomejr", null, arrayOf("friends"))
                true
            }
    }

    fun refuseFriend(name: String): Observable<Boolean>{
        return Observable.just(name)
            .map{
                val subscription: Packet = Presence(Presence.Type.unsubscribe)
                subscription.to = "$it@gomejr"
                XmppConnection.getConnection().sendPacket(subscription)
                true
            }
    }
}