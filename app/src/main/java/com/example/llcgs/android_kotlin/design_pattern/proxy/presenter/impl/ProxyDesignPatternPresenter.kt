package com.example.llcgs.android_kotlin.design_pattern.proxy.presenter.impl

import com.example.llcgs.android_kotlin.base.rx.MyObserver
import com.example.llcgs.android_kotlin.design_pattern.proxy.model.ProxyDesignPatternModel
import com.example.llcgs.android_kotlin.design_pattern.proxy.presenter.IProxyDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.proxy.view.ProxyDesignPatternView

/**
 * com.example.llcgs.android_kotlin.design_pattern.proxy.presenter.impl.ProxyDesignPatternPresenter
 * @author liulongchao
 * @since 2017/12/27
 */
class ProxyDesignPatternPresenter(private val view: ProxyDesignPatternView) : IProxyDesignPatternPresenter {

    private val model = ProxyDesignPatternModel()

    override fun buy(startingPlace: String, destination: String, which: String) {
        model.buy(startingPlace, destination, which)
                .subscribe(object : MyObserver<String>() {
                    override fun onNext(t: String) {
                        view.onGetPrice(t)
                    }
                })
    }
}