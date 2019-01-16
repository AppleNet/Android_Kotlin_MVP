package com.example.llcgs.android_kotlin.base.view

import io.reactivex.disposables.Disposable

/**
 * com.example.llcgs.android_kotlin.base.view.BaseView
 * @author liulongchao
 * @since 2017/7/18
 */


interface BaseView {

    // 展示进度框
    fun showLoadingDialog()
    // 关闭进度框
    fun dismissLoadingDialog()
    // 展示toast
    fun showToast(message: String)
    // 显示内容不分view
    fun showContentView()
    /**
     * 显示异常部分view
     *
     * @param imageRes 显示的资源图片
     * @param message 显示的信息
     */
    fun showException(imageRes: Int, message: String)

    // 添加Disposable
    fun addDisposable(disposable: Disposable)
}