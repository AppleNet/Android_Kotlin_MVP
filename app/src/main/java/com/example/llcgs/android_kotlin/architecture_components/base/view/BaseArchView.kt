package com.example.llcgs.android_kotlin.architecture_components.base.view

import android.arch.lifecycle.LifecycleOwner
import io.reactivex.disposables.Disposable

/**
 * com.example.llcgs.android_kotlin.architecture_components.base.view.BaseArchView
 * @author liulongchao
 * @since 2017/11/27
 */
interface BaseArchView: LifecycleOwner {

    // 展示进度框
    fun showLoadingDialog()

    // 关闭进度框
    fun dismissLoadingDialog()

    /**
     * 获取数据失败
     */
    fun onObtainFail(ex: Exception)

    // 展示吐司
    fun showToast(text: Any)

    fun showToast(resId: Int)

    /**
     * 添加 回调
     */
    fun addDisposable(disposable: Disposable)

}