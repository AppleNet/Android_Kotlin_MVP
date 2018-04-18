package com.example.llcgs.android_kotlin.net.socket_tcp.presenter.impl

import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.net.socket_tcp.model.SocketTCPModel
import com.example.llcgs.android_kotlin.net.socket_tcp.presenter.ISocketTCPPresenter
import com.example.llcgs.android_kotlin.net.socket_tcp.view.SocketTCPView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.Socket

/**
 * com.example.llcgs.android_kotlin.net.socket_tcp.presenter.impl.SocketTCPPresenter
 * @author liulongchao
 * @since 2018/4/17
 */
class SocketTCPPresenter(private val view: SocketTCPView): ISocketTCPPresenter {

    private val model = SocketTCPModel()

    override fun createSocket(port: Int, backLog: Int) {
        model.createSocket(port, backLog)
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onGetClientSocket(it.accept())
            }
    }

    override fun closeSocket(socket: Socket) {
        model.closeSocket(socket)
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onCloseSocketResult(it)
            }
    }

    override fun sendMsg(socket: Socket, msg: String) {
        model.sendMsg(socket, msg)
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onSendMsgResult(it)
            }
    }

    override fun receiveMsg(socket: Socket) {
        model.receiveMsg(socket)
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onGetReceiveMsg(it)
            }
    }

}