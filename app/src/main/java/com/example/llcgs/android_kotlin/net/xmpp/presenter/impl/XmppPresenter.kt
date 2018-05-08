package com.example.llcgs.android_kotlin.net.xmpp.presenter.impl

import android.content.Context
import android.support.v7.app.AlertDialog
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.base.rx.RxBus
import com.example.llcgs.android_kotlin.net.xmpp.model.XmppModel
import com.example.llcgs.android_kotlin.net.xmpp.presenter.IXmppPresenter
import com.example.llcgs.android_kotlin.net.xmpp.view.XmppView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jivesoftware.smack.packet.Packet

/**
 * com.example.llcgs.android_kotlin.net.xmpp.presenter.impl.XmppPresenter
 * @author liulongchao
 * @since 2018/5/4
 */
class XmppPresenter(private val view: XmppView): IXmppPresenter {

    private val model = XmppModel()

    override fun login(userName: String, userPwd: String) {
        model.login(userName, userPwd)
            .doOnSubscribe {
                view.addDisposable(it)
                view.showLoadingDialog()
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate {
                view.dismissLoadingDialog()
            }
            .subscribe {
                view.onLoginSuccess(it)
            }

    }

    override fun logOut() {
        model.logOut()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                //
            }
    }

    override fun addFriend(name: String) {
        model.addFriend(name)
            .doOnSubscribe {
                view.addDisposable(it)
                view.showLoadingDialog()
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate {
                view.dismissLoadingDialog()
            }
            .subscribe{
                view.onAddSuccess(it)
            }
    }

    override fun registerUser(userName: String, userPwd: String, userEmail: String, userAlias: String) {
        model.registerUser(userName, userPwd, userEmail, userAlias)
            .doOnSubscribe {
                view.addDisposable(it)
                view.showLoadingDialog()
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate {
                view.dismissLoadingDialog()
            }
            .subscribe {
                view.onRegisterSuccess(it)
            }
    }

    override fun getFriendList() {
        model.getFriendList()
            .doOnSubscribe {
                view.addDisposable(it)
                view.showLoadingDialog()
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate {
                view.dismissLoadingDialog()
            }
            .subscribe {
                view.onGetFriendList(it)
            }
    }

    override fun addSubscriptionListener() {
        model.addSubscriptionListener()
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            }
        RxBus.getInstance().tObservable(Packet::class.java)
            .subscribe {
                view.onGetRequest(it)
            }
    }

    override fun acceptFriend(name: String) {
        model.acceptFriend(name)
            .subscribe {

            }
    }

    override fun refuseFriend(name: String) {
        model.refuseFriend(name)
            .subscribe()
    }
}