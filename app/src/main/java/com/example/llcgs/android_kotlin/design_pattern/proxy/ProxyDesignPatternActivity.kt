package com.example.llcgs.android_kotlin.design_pattern.proxy

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternActivity
import com.example.llcgs.android_kotlin.design_pattern.proxy.presenter.IProxyDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.proxy.presenter.impl.ProxyDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.proxy.view.ProxyDesignPatternView
import kotlinx.android.synthetic.main.activity_design_pattern_proxy.*

/**
 * com.example.llcgs.android_kotlin.design_pattern.proxy.ProxyDesignPatternActivity
 * @author liulongchao
 * @since 2017/12/27
 */
class ProxyDesignPatternActivity : BaseDesignPatternActivity<IProxyDesignPatternPresenter>(), ProxyDesignPatternView, AdapterView.OnItemSelectedListener, View.OnClickListener {

    /**
     *  代理模式给某一个对象提供一个代理对象，并由代理对象控制对原对象的引用
     *   相当于一个人或者机构 代表另一个人或者机构，类似与中介
     *
     *   // 通过代理人买机票
     * */

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var array: Array<String>
    private var which:String = ""

    override fun createPresenter() = ProxyDesignPatternPresenter(this)

    override fun getUrl() = "http://www.cnblogs.com/java-my-life/archive/2012/04/23/2466712.html"

    override fun getLayoutId() = R.layout.activity_design_pattern_proxy

    override fun initViews() {
        setSupportActionBar(toolBar)
        array = resources.getStringArray(R.array.spinner)
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, array)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
        buy.setOnClickListener(this)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        which = position.toString()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buy -> {
                //
                mPresenter.buy(start.text.toString(), destination.text.toString(), which)
            }
        }
    }

    override fun onGetPrice(string: String) {
        buy.text = string
    }

}