package com.example.llcgs.android_kotlin.material.douya.detail

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.base.lifecycleevent.LifeCycleEvent
import com.example.llcgs.android_kotlin.base.lifecycleevent.LifecycleHelper
import com.example.llcgs.android_kotlin.base.rx.exception.ObtainException
import com.example.llcgs.android_kotlin.material.base.BaseMaterialPresenter
import com.example.llcgs.android_kotlin.material.base.BaseMaterialView
import com.example.llcgs.android_kotlin.utils.TransitionUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

/**
 * com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
 * @author liulongchao
 * @since 2017/12/11
 */
abstract class BaseDetailActivity<P : BaseMaterialPresenter>: AppCompatActivity(), BaseMaterialView {

    protected lateinit var mPresenter: P
    private var compositeDisposable: CompositeDisposable? = null
    private val lifecycleSubject: BehaviorSubject<LifeCycleEvent> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE)
        TransitionUtils.setupTransitionBeforeDecorate(this)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        TransitionUtils.postponeTransition(this)
        mPresenter = createPresenter()

        onGetIntentData(intent)
        initViews()
        initData()
    }

    abstract fun createPresenter(): P
    abstract fun getLayoutId(): Int
    abstract fun initViews()

    open protected fun initData(){

    }

    open protected fun onGetIntentData(intent: Intent){

    }

    override fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null){
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
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
        super.onDestroy()
    }

    override fun lifecycle(): Observable<LifeCycleEvent> = lifecycleSubject.hide()

    override fun <T : Any?> bindUntilEvent(event: LifeCycleEvent): LifecycleTransformer<T> = RxLifecycle.bindUntilEvent(lifecycleSubject, event)

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> = RxLifecycle.bind(lifecycleSubject, LifecycleHelper.activityLifecycle())

    override fun showLoadingDialog() {
        //Toast.makeText(this, "show loading", Toast.LENGTH_SHORT).show()
    }

    override fun dismissLoadingDialog() {
        //Toast.makeText(this, "dismiss loading", Toast.LENGTH_SHORT).show()
    }

    override fun onObtainFail(exception: ObtainException) {
    }
}