package com.example.llcgs.android_kotlin.design_pattern.list

import android.support.v7.widget.LinearLayoutManager
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternActivity
import com.example.llcgs.android_kotlin.design_pattern.list.adapter.DesignPatternMainAdapter
import com.example.llcgs.android_kotlin.design_pattern.list.presenter.IDesignPatternMainPresenter
import com.example.llcgs.android_kotlin.design_pattern.list.presenter.impl.DesignPatternMainPresenter
import com.example.llcgs.android_kotlin.design_pattern.list.view.DesignPatternMainView
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.activity_design_pattern_main.*

/**
 * com.example.llcgs.android_kotlin.design_pattern.list.DesignPatternMainActivity
 * @author liulongchao
 * @since 2017/12/26
 */
@RouterRule("Design_Pattern")
class DesignPatternMainActivity: BaseDesignPatternActivity<IDesignPatternMainPresenter>(), DesignPatternMainView {

    private lateinit var adapter: DesignPatternMainAdapter

    override fun createPresenter(): IDesignPatternMainPresenter= DesignPatternMainPresenter(this)

    override fun getLayoutId(): Int= R.layout.activity_design_pattern_main

    override fun initViews() {
        adapter = DesignPatternMainAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

    override fun initData() {
        mPresenter.fetchDesignPatter()
    }

    override fun onGetDesignPattern(list: List<String>) {
        adapter.setNewData(list)
    }
}