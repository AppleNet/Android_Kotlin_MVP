package com.example.llcgs.android_kotlin.design_pattern.wrapper

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternActivity
import com.example.llcgs.android_kotlin.design_pattern.wrapper.presenter.IWrapperDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.wrapper.presenter.impl.WrapperDesignPatternPresenter

/**
 * com.example.llcgs.android_kotlin.design_pattern.wrapper.WrapperDesignPatternActivity
 * @author liulongchao
 * @since 2017/12/27
 */
class WrapperDesignPatternActivity : BaseDesignPatternActivity<IWrapperDesignPatternPresenter>() {

    override fun createPresenter() = WrapperDesignPatternPresenter()

    override fun getLayoutId()= R.layout.activity_design_pattern_wrapper

    override fun getUrl() = "http://www.cnblogs.com/java-my-life/archive/2012/04/20/2455726.html"

    override fun initViews() {

    }
}