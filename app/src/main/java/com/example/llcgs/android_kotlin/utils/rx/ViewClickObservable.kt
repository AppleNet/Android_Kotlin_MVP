package com.example.llcgs.android_kotlin.utils.rx;

import android.os.Looper
import android.view.View
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.atomic.AtomicBoolean

class ViewClickObservable(private var view: View): Observable<Any>() {

    override fun subscribeActual(observer: Observer<in Any>) {
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.setOnClickListener(listener)
    }

    class Listener(private var view: View, private var observer: Observer<in Any>): View.OnClickListener, Disposable{

        private val isDisposable: AtomicBoolean = AtomicBoolean()

        override fun onClick(v: View?) {
            if (!isDisposed) {
                observer.onNext(com.jakewharton.rxbinding2.internal.Notification.INSTANCE)
            }
        }

        override fun isDisposed(): Boolean {
           return isDisposable.get()
        }

        override fun dispose() {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                // 主线程
                view.setOnClickListener(null)
            } else {
                //子线程
                AndroidSchedulers.mainThread().scheduleDirect { view.setOnClickListener(null) }
            }
        }
    }
}
