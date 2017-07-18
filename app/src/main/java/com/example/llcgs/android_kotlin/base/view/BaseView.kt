package com.example.llcgs.android_kotlin.base.view

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
}