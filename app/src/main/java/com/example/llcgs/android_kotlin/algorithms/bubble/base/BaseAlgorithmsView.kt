package com.example.llcgs.android_kotlin.algorithms.bubble.base

import com.example.llcgs.android_kotlin.base.lifecycleevent.LifeCycleEvent
import com.example.llcgs.android_kotlin.base.rx.exception.ObtainException
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.disposables.Disposable

/**
 * com.example.llcgs.android_kotlin.material.base.BaseMaterialView
 * @author liulongchao
 * @since 2017/12/11
 */
interface BaseAlgorithmsView : LifecycleProvider<LifeCycleEvent> {

    // 展示进度框
    fun showLoadingDialog()

    // 关闭进度框
    fun dismissLoadingDialog()

    // 添加Disposable
    fun addDisposable(disposable: Disposable)

    fun onObtainFail(exception: ObtainException)
}