package com.example.llcgs.android_kotlin.net.xmpp.presenter

import com.example.llcgs.android_kotlin.net.base.BaseNetWorkPresenter

/**
 * com.example.llcgs.android_kotlin.net.xmpp.presenter.IXmppPresenter
 * @author liulongchao
 * @since 2018/5/4
 */
interface IXmppPresenter: BaseNetWorkPresenter {

    fun registerUser(userName: String, userPwd: String, userEmail: String, userAlias: String)

    fun login(userName: String, userPwd: String)

    fun logOut()

    fun addFriend(name: String)

    fun getFriendList()

    /**监听添加好友申请*/
    fun addSubscriptionListener()

    fun acceptFriend(name: String)

    fun refuseFriend(name: String)
}