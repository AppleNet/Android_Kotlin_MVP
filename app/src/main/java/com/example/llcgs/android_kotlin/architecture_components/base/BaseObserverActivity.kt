package com.example.llcgs.android_kotlin.architecture_components.base

import android.arch.lifecycle.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.example.llcgs.android_kotlin.architecture_components.base.presenter.BaseArchPresenter

/**
 * com.example.llcgs.android_kotlin.architecture_components.base.BaseOwnerActivity
 * @author liulongchao
 * @since 2017/11/27
 *
 */
class BaseObserverActivity: AppCompatActivity(), LifecycleObserver{


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart() {
        super.onStart()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
        super.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPause() {
        super.onPause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStop() {
        super.onStop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        super.onDestroy()
    }

}