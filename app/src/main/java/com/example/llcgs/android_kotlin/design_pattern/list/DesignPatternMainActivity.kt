package com.example.llcgs.android_kotlin.design_pattern.list

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternActivity
import com.example.llcgs.android_kotlin.design_pattern.facade.FacadeDesignPatternActivity
import com.example.llcgs.android_kotlin.design_pattern.flyweight.FlyweightDesignPatternActivity
import com.example.llcgs.android_kotlin.design_pattern.list.adapter.DesignPatternMainAdapter
import com.example.llcgs.android_kotlin.design_pattern.list.presenter.IDesignPatternMainPresenter
import com.example.llcgs.android_kotlin.design_pattern.list.presenter.impl.DesignPatternMainPresenter
import com.example.llcgs.android_kotlin.design_pattern.list.view.DesignPatternMainView
import com.example.llcgs.android_kotlin.design_pattern.proxy.ProxyDesignPatternActivity
import com.example.llcgs.android_kotlin.design_pattern.wrapper.WrapperDesignPatternActivity
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.activity_design_pattern_main.*

/**
 * com.example.llcgs.android_kotlin.design_pattern.list.DesignPatternMainActivity
 * @author liulongchao
 * @since 2017/12/26
 */
@RouterRule("Design_Pattern")
class DesignPatternMainActivity: BaseDesignPatternActivity<IDesignPatternMainPresenter>(), DesignPatternMainView, BaseQuickAdapter.OnItemClickListener {

    private lateinit var adapter: DesignPatternMainAdapter

    override fun createPresenter(): IDesignPatternMainPresenter= DesignPatternMainPresenter(this)

    override fun getLayoutId(): Int= R.layout.activity_design_pattern_main

    override fun getUrl() = ""

    override fun initViews() {
        setSupportActionBar(toolBar)
        adapter = DesignPatternMainAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        adapter.onItemClickListener = this
    }

    override fun initData() {
        mPresenter.fetchDesignPatter()
    }

    override fun onGetDesignPattern(list: List<String>) {
        adapter.setNewData(list)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when(position){
            0 ->{
                startActivity(Intent(this, FacadeDesignPatternActivity::class.java))
            }
            1 ->{
                startActivity(Intent(this, FlyweightDesignPatternActivity::class.java))
            }
            2 ->{
                startActivity(Intent(this, ProxyDesignPatternActivity::class.java))
            }
            3 ->{
                startActivity(Intent(this, WrapperDesignPatternActivity::class.java))
            }
        }
    }
}