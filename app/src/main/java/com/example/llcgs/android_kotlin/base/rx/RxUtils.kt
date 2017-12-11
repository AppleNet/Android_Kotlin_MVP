package com.example.llcgs.android_kotlin.base.rx

import android.app.Activity
import android.text.TextUtils
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.base.lifecycleevent.FragmentLifeCycleEvent
import com.example.llcgs.android_kotlin.base.rx.bean.HTTP_STATUS_ERROR
import com.example.llcgs.android_kotlin.base.rx.bean.TagReceiver
import com.example.llcgs.android_kotlin.base.rx.bean.Wrapper
import com.example.llcgs.android_kotlin.base.rx.bean.WrapperBean
import com.example.llcgs.android_kotlin.base.rx.exception.ObtainException
import com.example.llcgs.android_kotlin.material.base.BaseMaterialView
import com.qihoo360.loader2.Constant
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.base.rx.RxUtils
 * @author liulongchao
 * @since 2017/12/11
 */
object RxUtils {

    /**
     *  基本配置
     *
     * */
    fun <T> obtainStartFinishTransformer(view: BaseMaterialView): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.doOnSubscribe(obtainStartConsumer(view))
                    .doOnTerminate(obtainFinishAction(view))
                    .doFinally {
                        view.dismissLoadingDialog()
                    }
        }
    }

    /**
     *  基本配置(带生命周期)
     *
     * */
    fun <T> defaultTransformer(view: BaseMaterialView): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream
                    .compose(rxLifecycleTransformer<T>(view))
                    .compose(addLifecycleTransformer<T>(view))
                    .compose(schedulersTransformer<T>())
                    .compose(obtainStartFinishTransformer<T>(view))
        }
    }

    /**
     *  处理线程切换
     *
     * */
    fun <T> schedulersTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     *  处理服务器返回数据。Observable＜WrapperBean＜DataBean＞＞
     * */
    fun <T, R> wrap2DataTransformer(view: BaseMaterialView): ObservableTransformer<T, R> {
        return ObservableTransformer { tObservable ->
            tObservable.filter(obtainErrorPredicate<T>(view))
                    .flatMap(Function<T, ObservableSource<R>> {
                        if (it is WrapperBean<*>) {
                            val data = it.data
                            if (data != null) {
                                Observable.just(data)
                            }
                        }
                        Observable.error(ObtainException(code = "500", message = "网络链接失败，请稍后重试"))
                    })
                    .doOnError(obtainErrorConsumer(view))
                    .onErrorResumeNext(ObtainErrorResumeFunction<R>())

        }
    }

    /**
     *  处理服务器返回数据。Observable＜WrapperBean＞
     * */
    fun <T> wrapDataTransformer(view: BaseMaterialView): ObservableTransformer<T, T> {
        return ObservableTransformer { tObservable ->
            tObservable.filter(obtainErrorPredicate<T>(view))
                    .doOnError(obtainErrorConsumer(view))
                    .onErrorResumeNext(ObtainErrorResumeFunction<T>())
        }
    }

    /**
     *  统一处理服务端返回的code值
     * */
    fun <T> obtainErrorPredicate(view: BaseMaterialView): Predicate<T> {
        return Predicate {
            if (it is TagReceiver) {
                if (HTTP_STATUS_ERROR == it.code) {
                    view.onObtainFail(ObtainException(it.code, it.message, it.tag))
                    return@Predicate false
                }
            }
            if (it is Wrapper<*>) {
                // 后端定义成功的code 一般情况下 都是0
                if (!"0".equals(it.code, ignoreCase = true)) {
                    var message = it.message
                    val showMsg = it.showMsg
                    message = if (TextUtils.isEmpty(showMsg)) if (TextUtils.isEmpty(message)) "网络链接失败，请稍后重试" else message else showMsg
                    view.onObtainFail(ObtainException(it.code, message, it.tag))
                    return@Predicate false
                }
            }
            if (it is ObtainException) {
                view.onObtainFail(it as ObtainException)
                return@Predicate false
            }
            true
        }
    }

    /**
     * 统一 错误处理
     * */
    fun obtainErrorConsumer(view: BaseMaterialView): Consumer<Throwable> {
        return Consumer { throwable ->
            if (throwable is ObtainException) {
                view.onObtainFail(throwable)
            } else {
                view.onObtainFail(ObtainException("404", "网络链接失败，请稍后重试"))
            }
        }
    }


    fun <T> obtainStartConsumer(view: BaseMaterialView): Consumer<T> {
        return Consumer {
            view.showLoadingDialog()
        }
    }

    /**
     * 组合事件的 整体事件流结束的回调(包含 onError onCompleted)
     */
    fun obtainFinishAction(view: BaseMaterialView): Action {
        return Action {
            view.dismissLoadingDialog()
        }
    }


    /**
     *  添加开始监听生命周期
     *
     * */
    fun <T> addLifecycleTransformer(view: BaseMaterialView): ObservableTransformer<T, T> =
            ObservableTransformer { upstream -> upstream.doOnSubscribe { disposable -> view.addDisposable(disposable) } }

    /**
     * 添加结束监听生命周期
     *
     * */
    fun <T> rxLifecycleTransformer(view: BaseMaterialView): LifecycleTransformer<T> {
        if (view is Activity) {
            return view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY)
        }
        return view.bindUntilEvent(FragmentLifeCycleEvent.DESTROY_VIEW)
    }

    class ObtainErrorResumeFunction<T> : Function<Throwable, ObservableSource<T>> {
        override fun apply(t: Throwable): ObservableSource<T> = Observable.empty<T>()
    }

}