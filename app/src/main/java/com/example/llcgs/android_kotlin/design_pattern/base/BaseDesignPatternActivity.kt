package com.example.llcgs.android_kotlin.design_pattern.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.base.lifecycleevent.LifeCycleEvent
import com.example.llcgs.android_kotlin.base.lifecycleevent.LifecycleHelper
import com.example.llcgs.android_kotlin.base.rx.exception.ObtainException
import com.example.llcgs.android_kotlin.material.douya.webview.MaterialWebViewActivity
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

/**
 * com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternActivity
 * @author liulongchao
 * @since 2017/12/26
 */
abstract class BaseDesignPatternActivity<P: BaseDesignPatternPresenter>: AppCompatActivity(), BaseDesignPatternView {

    protected lateinit var mPresenter: P
    private var compositeDisposable: CompositeDisposable? = null
    private val lifecycleSubject: BehaviorSubject<LifeCycleEvent> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(getLayoutId())
        mPresenter = createPresenter()

        initViews()
        initData()
    }

    abstract fun createPresenter(): P
    abstract fun getLayoutId(): Int
    abstract fun getUrl():String

    abstract fun initViews()

    protected open fun initData() = Unit

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.design_pattern, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                finish()
                return true
            }
            R.id.action_detail ->{
                val intent = Intent(this, MaterialWebViewActivity::class.java).apply {
                    if(getUrl().isNotEmpty()){
                        putExtra("EXTRA_URL", getUrl())
                    }
                }
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}