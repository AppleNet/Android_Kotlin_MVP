package com.example.llcgs.android_kotlin.design_pattern.simplefactory;

import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.base.rx.exception.ObtainException
import com.example.llcgs.android_kotlin.design_pattern.simplefactory.presenter.impl.SimpleFactoryPresenter
import com.example.llcgs.android_kotlin.design_pattern.simplefactory.view.SimpleFactoryView

/**
 * com.example.llcgs.android_kotlin.design_pattern.simplefactory.SimpleFactoryActivity
 * @author liulongchao
 * @since 2019/2/18
 */
class SimpleFactoryActivity: BaseActivity<SimpleFactoryView, SimpleFactoryPresenter>(), SimpleFactoryView {

    override fun createPresenter()= SimpleFactoryPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_factory)
    }

    override fun onObtainFail(exception: ObtainException) {
    }
}
