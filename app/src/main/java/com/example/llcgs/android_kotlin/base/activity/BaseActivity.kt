package com.example.llcgs.android_kotlin.base.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.base.lifecycleevent.LifeCycleEvent
import com.example.llcgs.android_kotlin.base.lifecycleevent.LifecycleHelper
import com.example.llcgs.android_kotlin.base.presenter.BasePresenter
import com.example.llcgs.android_kotlin.base.view.BaseView
import com.example.llcgs.android_kotlin.kotlin.coroutines.cancel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

/**
 * com.example.llcgs.android_kotlin.base.activity.BaseActivity
 * @author liulongchao
 * @since 2017/5/18
 */


abstract class BaseActivity<V, P : BasePresenter<V>> : AppCompatActivity(), BaseView, LifecycleProvider<LifeCycleEvent> {

    protected lateinit var mPresenter: P
    protected var compositeDisposable: CompositeDisposable? = null
    private val lifecycleSubject: BehaviorSubject<LifeCycleEvent> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE)
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
    }

    abstract fun createPresenter(): P

    override fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null){
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    fun Disposable.addDisposable(compositeDisposable: CompositeDisposable?){
        addDisposable(this)
    }

    override fun onStart() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.START)
        super.onStart()
    }

    override fun onResume() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.RESUME)
        super.onResume()
    }

    override fun onPause() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.STOP)
        super.onStop()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY)
        if (compositeDisposable != null){
            compositeDisposable?.clear()
        }
        // 协程取消
        cancel()
        super.onDestroy()
    }

    override fun lifecycle(): Observable<LifeCycleEvent> = lifecycleSubject.hide()

    /**
     * 该方法指定在哪个生命周期方法调用时取消订阅
     *
     * */
    override fun <T : Any?> bindUntilEvent(event: LifeCycleEvent): LifecycleTransformer<T> = RxLifecycle.bindUntilEvent(lifecycleSubject, event)

    /**
     * 在某个生命周期进行绑定，在对应的生命周期进行订阅解除
     *
     *  在onResume()进行绑定订阅，则在onPause()进行解除订阅，生命周期是两两对应的
     *
     * */
    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> = RxLifecycle.bind(lifecycleSubject, LifecycleHelper.activityLifecycle())

    override fun showContentView() {
    }

    override fun showToast(message: String) {
    }

    override fun showException(imageRes: Int, message: String) {
    }

    override fun showLoadingDialog() {
        Toast.makeText(this, "show loading", Toast.LENGTH_SHORT).show()
    }

    override fun dismissLoadingDialog() {
        Toast.makeText(this, "dismiss loading", Toast.LENGTH_SHORT).show()
    }
}