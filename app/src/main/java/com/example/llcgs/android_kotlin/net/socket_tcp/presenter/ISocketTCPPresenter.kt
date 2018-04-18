package com.example.llcgs.android_kotlin.net.socket_tcp.presenter

import com.example.llcgs.android_kotlin.net.base.BaseNetWorkPresenter
import java.net.Socket

/**
 * com.example.llcgs.android_kotlin.net.socket_tcp.presenter.ISocketTCPPresenter
 * @author liulongchao
 * @since 2018/4/17
 */
interface ISocketTCPPresenter: BaseNetWorkPresenter {

    fun createSocket(port: Int, backLog: Int)
    fun closeSocket(socket: Socket)
    fun sendMsg(socket: Socket, msg:String)
    fun receiveMsg(socket: Socket)
}