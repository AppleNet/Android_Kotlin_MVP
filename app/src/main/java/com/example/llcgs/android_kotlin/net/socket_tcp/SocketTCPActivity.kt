package com.example.llcgs.android_kotlin.net.socket_tcp

import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.net.base.BaseNetWorkActivity
import com.example.llcgs.android_kotlin.net.socket_tcp.presenter.ISocketTCPPresenter
import com.example.llcgs.android_kotlin.net.socket_tcp.presenter.impl.SocketTCPPresenter
import com.example.llcgs.android_kotlin.net.socket_tcp.view.SocketTCPView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_socket_ip.*
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

/**
 * com.example.llcgs.android_kotlin.net.socket_tcp.SocketTCPActivity
 * @author liulongchao
 * @since 2018/4/17
 */
class SocketTCPActivity: BaseNetWorkActivity<ISocketTCPPresenter>(), SocketTCPView, View.OnClickListener {

    private var socket: Socket? = null

    override fun createPresenter(): ISocketTCPPresenter = SocketTCPPresenter(this)

    override fun getLayoutId()= R.layout.activity_socket_ip

    override fun initViews() {
        button47.setOnClickListener(this)
        button48.setOnClickListener(this)
    }

    override fun onGetClientSocket(socket: Socket) {
        this.socket = socket
    }

    override fun onSendMsgResult(flag: String) {
        editText3.setText(flag)
    }

    override fun onGetReceiveMsg(txt: String) {
        if (socket != null && txt.isNotEmpty()){
            mPresenter.sendMsg(socket!!, txt)
        }
    }

    override fun onCloseSocketResult(flag: Boolean) {

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button47 ->{
                // 发送消息
                val txt = editText3.text.toString()
                Observable.just(txt)
                    .doOnSubscribe {
                        addDisposable(it)
                    }
                    .compose(bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
                    .map {
                        socket = Socket("10.133.37.213",600)
                        val writer = BufferedWriter(OutputStreamWriter(socket!!.getOutputStream()))
                        writer.write(txt)
                        writer.flush()
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        socket!!.close()
                    }
            }
            R.id.button48 ->{
                // 接收消息
                Observable.just("")
                    .doOnSubscribe {
                        addDisposable(it)
                    }
                    .compose(bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
                    .map {
                        val reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))
                        val msg = reader.readLine()
                        msg
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        editText3.setText(it)
                        socket!!.close()
                    }


            }
        }
    }
}