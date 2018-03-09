package com.example.llcgs.android_kotlin.modules.activity.standard

import android.os.Bundle
import android.os.PersistableBundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.modules.activity.standard.presenter.impl.StandardPresenter
import com.example.llcgs.android_kotlin.modules.activity.standard.view.StandardView

/**
 * com.example.llcgs.android_kotlin.modules.activity.standard.StandardActivity
 * @author liulongchao
 * @since 2018/3/9
 */
class StandardActivity: BaseActivity<StandardView, StandardPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_standard)
    }

    override fun createPresenter() = StandardPresenter()
}