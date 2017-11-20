package com.example.llcgs.android_kotlin.mvvm.viewstubs.view

import android.view.View
import com.example.llcgs.android_kotlin.mvvm.viewstubs.model.ViewStubModel

/**
 * com.example.llcgs.android_kotlin.mvvm.viewstubs.view.ViewStubView
 * @author liulongchao
 * @since 2017/11/20
 */
interface ViewStubView {

    fun onGetViews(view: View, viewStubModel: ViewStubModel)
}