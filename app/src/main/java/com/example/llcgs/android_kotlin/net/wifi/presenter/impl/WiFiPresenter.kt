package com.example.llcgs.android_kotlin.net.wifi.presenter.impl

import android.content.Context
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.net.wifi.model.WiFiModel
import com.example.llcgs.android_kotlin.net.wifi.presenter.IWiFiPresenter
import com.example.llcgs.android_kotlin.net.wifi.view.WiFiView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.net.wifi.presenter.impl.WiFiPresenter
 * @author liulongchao
 * @since 2018/4/27
 */
class WiFiPresenter(private val view: WiFiView): IWiFiPresenter {

    private val model = WiFiModel(view as Context)

    override fun getScanResult() {
        model.scan()
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onGetScanResult(it)
            }
    }
}