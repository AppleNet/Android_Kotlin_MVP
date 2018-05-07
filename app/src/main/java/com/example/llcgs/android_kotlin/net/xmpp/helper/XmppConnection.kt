package com.example.llcgs.android_kotlin.net.xmpp.helper

import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.XMPPConnection

/**
 * com.example.llcgs.android_kotlin.net.xmpp.helper.XmppConnection
 * @author liulongchao
 * @since 2018/5/4
 */
object XmppConnection {

    private var connection: XMPPConnection? = null
    //配置文件  参数（服务地地址，端口号，域）
    private val config = ConnectionConfiguration("10.133.37.213", 5222, "gomejr").apply {
        // 设置断网重连 默认为true
        isReconnectionAllowed = true
        // 设置登录状态 true-为在线
        setSendPresence(true)
        // 设置不需要SAS验证
        isSASLAuthenticationEnabled = true
    }

    fun getConnection(): XMPPConnection{
        XMPPConnection.DEBUG_ENABLED = true
        // 开启连接
        if (connection == null){
            connection = XMPPConnection(config)
        }
        if (connection?.isConnected == true){
            return connection ?: XMPPConnection(config)
        }else{
            connection?.connect()
        }
        return connection?: XMPPConnection(config)
    }

}