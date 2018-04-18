package com.example.llcgs.android_kotlin.net.socket_tcp.model

import com.example.llcgs.android_kotlin.net.base.BaseNetWorkModel
import io.reactivex.Observable
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.net.Socket

/**
 * com.example.llcgs.android_kotlin.net.socket_tcp.model.SocketTCPModel
 * @author liulongchao
 * @since 2018/4/17
 */
class SocketTCPModel: BaseNetWorkModel {

    private var writer: BufferedWriter? = null
    private var reader: BufferedReader? = null

    fun createSocket(port: Int, backLog: Int): Observable<ServerSocket> =
        Observable.just(ServerSocket(port, backLog))

    fun sendMsg(socket: Socket, msg: String): Observable<String>{
        // 要发送的消息写入BufferWriter中
        writer = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
        // 添加空行分隔符
        writer?.write(msg + "\n")
        //刷新 发送
        writer?.flush()
        return Observable.just(msg)
    }

    fun receiveMsg(socket: Socket): Observable<String>{
        // 保存到读Buffer
        reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        // 将接收到的信息保存到字符串
        val txt = "Server send: "+reader?.readLine()
        return Observable.just(txt)
    }

    fun closeSocket(socket: Socket): Observable<Boolean>{
        if (reader != null){
            reader?.close()
        }
        if (writer != null){
            writer?.close()
        }
        socket.close()
        return Observable.just(true)
    }
}