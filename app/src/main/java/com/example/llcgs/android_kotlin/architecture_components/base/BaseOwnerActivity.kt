package com.example.llcgs.android_kotlin.architecture_components.base

import android.arch.lifecycle.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.example.llcgs.android_kotlin.architecture_components.base.presenter.BaseArchPresenter
import com.example.llcgs.android_kotlin.architecture_components.base.view.BaseArchView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * com.example.llcgs.android_kotlin.architecture_components.base.BaseOwnerActivity
 * @author liulongchao
 * @since 2017/11/27
 */
abstract class BaseOwnerActivity<P: BaseArchPresenter>: AppCompatActivity(), BaseArchView{

    protected lateinit var mPresenter: P
    private var compositeDisposable: CompositeDisposable? = null

    private lateinit var mLifecycleRegistry:LifecycleRegistry


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setContentView(getLayoutId())
        mPresenter = createPresenter()

        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)

    }

    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun onResume() {
        super.onResume()
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    override fun onDestroy() {
        super.onDestroy()
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED)
    }

    override fun showLoadingDialog() {}

    override fun dismissLoadingDialog() {}

    override fun showToast(resId: Int) {}

    override fun showToast(text: Any) {}

    override fun onObtainFail(ex: Exception) {}

    override fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null){
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    abstract fun createPresenter(): P

    abstract fun getLayoutId(): Int

    override fun getLifecycle(): Lifecycle = mLifecycleRegistry

}