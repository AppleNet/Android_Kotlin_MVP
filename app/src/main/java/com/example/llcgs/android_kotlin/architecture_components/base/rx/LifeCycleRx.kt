package com.example.llcgs.android_kotlin.architecture_components.base.rx

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Looper
import android.support.annotation.MainThread
import com.gomejr.myf.core.kotlin.logD

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

/**
 * LifeCycleRx
 *
 * @author liulongchao
 * @since 2017/11/28
 *
 * 将GoogleLifeCycle 和RxJava 完美结合起来，抛弃RxLifeCycle使用 GoogleLifeCycle
 */
class LifeCycleRx<T> private constructor(private val mLifecycleOwner: LifecycleOwner) : ObservableTransformer<T, T>, LifecycleObserver {

    private val mSubject = PublishSubject.create<T>()

    private var mDisposable: Disposable? = null

    private var mData: T? = null

    private var mActive: Boolean = false

    private var mVersion = -1

    private var mLastVersion = -1

    @MainThread
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        assertMainThread()
        if (mLifecycleOwner.lifecycle.currentState != Lifecycle.State.DESTROYED) {

            mLifecycleOwner.lifecycle.addObserver(this)

            upstream.subscribe(object : Observer<T> {
                override fun onSubscribe(d: Disposable) {
                    assertMainThread()
                    mDisposable = d
                }

                override fun onNext(t: T) {
                    assertMainThread()
                    ++mVersion
                    mData = t
                    considerNotify()
                }

                override fun onError(e: Throwable) {
                    assertMainThread()
                    if (!mDisposable!!.isDisposed) {
                        mSubject.onError(e)
                    }
                }

                override fun onComplete() {
                    assertMainThread()
                    if (!mDisposable!!.isDisposed) {
                        mSubject.onComplete()
                    }
                }
            })
            return mSubject
        } else {
            return Observable.empty()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    internal fun onStateChange() {
        if (this.mLifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            if (mDisposable != null && !mDisposable!!.isDisposed) {
                mDisposable!!.dispose()
            }
            mLifecycleOwner.lifecycle.removeObserver(this)
        } else {
            this.activeStateChanged(LifeCycleRx.isActiveState(mLifecycleOwner.lifecycle.currentState))
        }
    }

    private fun activeStateChanged(newActive: Boolean) {
        if (newActive != mActive) {
            mActive = newActive
            considerNotify()
        }
    }

    private fun considerNotify() {
        if (mActive) {
            if (isActiveState(mLifecycleOwner.lifecycle.currentState)) {
                if (mLastVersion < mVersion) {
                    mLastVersion = mVersion
                    if (mDisposable != null && !mDisposable!!.isDisposed) {
                        mSubject.onNext(mData!!)
                    }
                }
            }
        }
    }

    private fun assertMainThread() {
        if (!isMainThread) {
            throw IllegalStateException("You should not use the Live Transformer at a background thread.")
        }
    }

    companion object {

        fun <T> bindLifecycle(owner: LifecycleOwner): ObservableTransformer<T, T> =
                LifeCycleRx(owner)

        private val isMainThread: Boolean
            get() = Looper.getMainLooper().thread === Thread.currentThread()

        private fun isActiveState(state: Lifecycle.State): Boolean =
                state.isAtLeast(Lifecycle.State.STARTED)
    }
}
