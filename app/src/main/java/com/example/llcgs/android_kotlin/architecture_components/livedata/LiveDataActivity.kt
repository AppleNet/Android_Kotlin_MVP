package com.example.llcgs.android_kotlin.architecture_components.livedata

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.base.BaseOwnerActivity
import com.example.llcgs.android_kotlin.architecture_components.livedata.presenter.ILiveDataPresenter
import com.example.llcgs.android_kotlin.architecture_components.livedata.presenter.impl.LiveDataPresenter

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.LiveDataActivity
 * @author liulongchao
 * @since 2017/11/28
 */
class LiveDataActivity:BaseOwnerActivity<ILiveDataPresenter>() {

    override fun createPresenter(): ILiveDataPresenter= LiveDataPresenter()

    override fun getLayoutId(): Int= R.layout.activity_livedata
}