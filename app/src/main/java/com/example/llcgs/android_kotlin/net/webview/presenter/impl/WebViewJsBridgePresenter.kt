package com.example.llcgs.android_kotlin.net.webview.presenter.impl

import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.net.webview.model.WebViewJsBridgeModel
import com.example.llcgs.android_kotlin.net.webview.presenter.IWebViewJsBridgePresenter
import com.example.llcgs.android_kotlin.net.webview.view.WebViewJsBridgeView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

/**
 * com.example.llcgs.android_kotlin.net.webview.presenter.impl.WebViewJsBridgePresenter
 * @author liulongchao
 * @since 2018/5/9
 */
class WebViewJsBridgePresenter(private var view: WebViewJsBridgeView): IWebViewJsBridgePresenter {

    private val model = WebViewJsBridgeModel()

    override fun getToken(jsonStr: String) {
        model.getToken(jsonStr)
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onJsSuccess("javascript:" + JSONObject(jsonStr).optString("callBackMethod") + "(" + it +")")
            }
    }

}