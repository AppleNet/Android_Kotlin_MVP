package com.example.llcgs.android_kotlin.customview

import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.customview.presenter.impl.CustomViewPresenter
import com.example.llcgs.android_kotlin.customview.view.CustomViewView
import com.lzh.nonview.router.anno.RouterRule

/**
 * com.example.llcgs.android_kotlin.customview.CustomViewActivity
 * @author liulongchao
 * @since 2018/11/10
 */
@RouterRule("CustomView")
class CustomViewActivity: BaseActivity<CustomViewView, CustomViewPresenter>() {

    override fun createPresenter()= CustomViewPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)
    }
}