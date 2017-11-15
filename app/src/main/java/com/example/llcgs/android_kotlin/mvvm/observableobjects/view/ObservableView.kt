package com.example.llcgs.android_kotlin.mvvm.observableobjects.view

import android.databinding.Observable
import android.view.View

/**
 * com.example.llcgs.android_kotlin.mvvm.observableobjects.view.ObservableView
 * @author liulongchao
 * @since 2017/11/15
 */
interface ObservableView {
    fun onClickListener(view: View)
    fun propertyCallback(p0: Observable?, p1: Int)
}