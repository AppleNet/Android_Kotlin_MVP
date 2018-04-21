package com.example.llcgs.android_kotlin.net.webservice.presenter.impl

import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.base.rx.RxBus
import com.example.llcgs.android_kotlin.net.webservice.model.WebServiceModel
import com.example.llcgs.android_kotlin.net.webservice.presenter.IWebServicePresenter
import com.example.llcgs.android_kotlin.net.webservice.view.WebServiceView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.net.webservice.presenter.impl.WebServicePresenter
 * @author liulongchao
 * @since 2018/4/21
 */
class WebServicePresenter(private val view: WebServiceView): IWebServicePresenter {

    private val model = WebServiceModel()

    override fun getWeather(cityName: String) {
        model.getWeather(cityName)
        RxBus.getInstance().tObservable(String::class.java)
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onShowWeather(it)
            }
    }
}