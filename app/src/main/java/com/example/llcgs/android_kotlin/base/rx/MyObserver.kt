/*
 * com.example.llcgs.android_kotlin.base.rx.MyObserver
 * @author liulongchao
 * @since 2018/12/12
 */

package com.example.llcgs.android_kotlin.base.rx;

import io.reactivex.Observer
import io.reactivex.disposables.Disposable


abstract class MyObserver<T>: Observer<T> {

    override fun onSubscribe(d: Disposable) {
    }

    override fun onComplete() {
    }

    abstract override fun onNext(t: T)

    override fun onError(e: Throwable) {
    }
}
