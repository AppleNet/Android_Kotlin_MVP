package com.example.llcgs.android_kotlin.architecture_components.viewmodel

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.base.BaseOwnerActivity
import com.example.llcgs.android_kotlin.architecture_components.viewmodel.adapter.ViewModelAdapter
import com.example.llcgs.android_kotlin.architecture_components.viewmodel.viewmodel.ViewModelViewModel
import com.gomejr.myf.core.kotlin.logD
import kotlinx.android.synthetic.main.activity_view_model.*
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.architecture_components.viewmodel.ViewModelActivity
 * @author liulongchao
 * @since 2017/11/30
 */
class ViewModelActivity : BaseOwnerActivity<ViewModelViewModel>(), BaseQuickAdapter.OnItemClickListener, Observer<String> {

    private lateinit var adapter: ViewModelAdapter
    private lateinit var list: List<String>

    override fun createPresenter() = ViewModelProviders.of(this).get(ViewModelViewModel::class.java)

    override fun getLayoutId(): Int = R.layout.activity_view_model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews() {
        pluginTitleTV.text = "ViewModel"
        list = resources.getStringArray(R.array.databinding_nba).toList()
        adapter = ViewModelAdapter()
        adapter.setNewData(list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this).apply { orientation = LinearLayout.HORIZONTAL }

        adapter.onItemClickListener = this
    }

    private fun initData() {
        mPresenter.getUrlLiveData()?.observe(this, this)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        mPresenter.fetchUrl(list[position])
    }

    override fun onChanged(t: String?) {
        Glide.with(this).load(t).into(imageShow)
    }
}