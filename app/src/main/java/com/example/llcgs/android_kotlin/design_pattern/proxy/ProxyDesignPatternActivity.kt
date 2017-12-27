package com.example.llcgs.android_kotlin.design_pattern.proxy

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternActivity
import com.example.llcgs.android_kotlin.design_pattern.proxy.presenter.IProxyDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.proxy.presenter.impl.ProxyDesignPatternPresenter
import com.example.llcgs.android_kotlin.material.webview.MaterialWebViewActivity
import kotlinx.android.synthetic.main.activity_design_pattern_proxy.*

/**
 * com.example.llcgs.android_kotlin.design_pattern.proxy.ProxyDesignPatternActivity
 * @author liulongchao
 * @since 2017/12/27
 */
class ProxyDesignPatternActivity: BaseDesignPatternActivity<IProxyDesignPatternPresenter>() {

    /**
     *  代理模式给某一个对象提供一个代理对象，并由代理对象控制对原对象的引用
     *   相当于一个人或者机构 代表另一个人或者机构，类似与中介
     *
     * */

    override fun createPresenter()= ProxyDesignPatternPresenter()

    override fun getUrl()= "http://www.cnblogs.com/java-my-life/archive/2012/04/23/2466712.html"

    override fun getLayoutId()= R.layout.activity_design_pattern_proxy

    override fun initViews() {
        setSupportActionBar(toolBar)
    }

}