package com.example.llcgs.android_kotlin.net.socket_tcp.view

import com.example.llcgs.android_kotlin.net.base.BaseNetWorkView
import java.net.Socket

/**
 * com.example.llcgs.android_kotlin.net.socket_tcp.view.SocketTCPView
 * @author liulongchao
 * @since 2018/4/17
 */
interface SocketTCPView: BaseNetWorkView {
    fun onGetClientSocket(socket: Socket)
    fun onSendMsgResult(flag: String)
    fun onGetReceiveMsg(txt: String)
    fun onCloseSocketResult(flag: Boolean)
}