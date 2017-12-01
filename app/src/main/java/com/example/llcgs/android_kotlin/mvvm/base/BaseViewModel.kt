package com.example.llcgs.android_kotlin.mvvm.base

import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.base.lifecycleevent.LifeCycleEvent
import com.example.llcgs.android_kotlin.base.lifecycleevent.LifecycleHelper
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import java.util.*
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
 * @author liulongchao
 * @since 2017/10/20
 */


abstract class BaseViewModel : Observable(), LifecycleProvider<LifeCycleEvent> {

    @Suppress("DEPRECATED_JAVA_ANNOTATION")
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    protected annotation class Command

    @Suppress("DEPRECATED_JAVA_ANNOTATION")
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    protected annotation class BindView

    private var compositeDisposable: CompositeDisposable? = null
    private val lifecycleSubject = BehaviorSubject.create<LifeCycleEvent>()

    init {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE)
    }

    protected fun addDisposable(disposable: Disposable){
        compositeDisposable = if (compositeDisposable == null) CompositeDisposable() else compositeDisposable
        compositeDisposable?.add(disposable)
    }

    override fun lifecycle(): io.reactivex.Observable<LifeCycleEvent> = lifecycleSubject.hide()

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> = RxLifecycle.bind(lifecycleSubject, LifecycleHelper.activityLifecycle())

    override fun <T : Any?> bindUntilEvent(event: LifeCycleEvent): LifecycleTransformer<T> = RxLifecycle.bindUntilEvent(lifecycleSubject, event)

}