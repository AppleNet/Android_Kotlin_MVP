package com.example.llcgs.android_kotlin.tinker

import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.tinker.presenter.impl.TinkerPresenter
import com.example.llcgs.android_kotlin.tinker.view.TinkerView

/**
 * com.example.llcgs.android_kotlin.tinker.TinkerActivity
 * @author liulongchao
 * @since 2018/11/12
 */
class TinkerActivity: BaseActivity<TinkerView, TinkerPresenter>() {

    override fun createPresenter()= TinkerPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tinker)
    }
}