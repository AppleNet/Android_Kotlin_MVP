package com.example.llcgs.android_kotlin.architecture_components.livedata

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.base.BaseOwnerActivity
import com.example.llcgs.android_kotlin.architecture_components.livedata.adapter.LiveDataAdapter
import com.example.llcgs.android_kotlin.architecture_components.livedata.bean.LiveDataBean
import com.example.llcgs.android_kotlin.architecture_components.livedata.presenter.ILiveDataPresenter
import com.example.llcgs.android_kotlin.architecture_components.livedata.presenter.impl.LiveDataPresenter
import com.example.llcgs.android_kotlin.architecture_components.livedata.view.LiveDataView
import com.example.llcgs.android_kotlin.utils.widget.refreshlayout.callback.OnRefreshListener
import kotlinx.android.synthetic.main.activity_livedata.*
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.LiveDataActivity
 * @author liulongchao
 * @since 2017/11/28
 */
class LiveDataActivity : BaseOwnerActivity<ILiveDataPresenter>(), LiveDataView, Observer<List<LiveDataBean>>, OnRefreshListener {

    private var mutableList = MutableLiveData<List<LiveDataBean>>()
    private lateinit var adapter: LiveDataAdapter
    private var currentIndex = 0

    override fun createPresenter(): ILiveDataPresenter = LiveDataPresenter(this)

    override fun getLayoutId(): Int = R.layout.activity_livedata

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews() {
        pluginTitleTV.text = "LiveData"
        adapter = LiveDataAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.loadMoreEnable = false
    }

    private fun initData() {
        refreshLayout.autoRefresh()
        val array = resources.getStringArray(R.array.databinding_nba)
        mPresenter.fetchList(array)
        mutableList.observe(this, this)
    }

    override fun onGetMutableLiveData(list: List<LiveDataBean>) {
        if (refreshLayout.isRefreshing) {
            refreshLayout.stopRefreshLayout()
        }
        mutableList.value = list
    }

    override fun onChanged(t: List<LiveDataBean>?) {
        // Update the UI
        adapter.setNewData(t)
    }

    override fun onRefresh() {
        // demo 只做下拉刷新 先不做上拉加载更多
        var array: Array<String>? = null
        when (currentIndex) {
            0 -> {
                array = resources.getStringArray(R.array.architecture)
            }
            1 ->{
                array = resources.getStringArray(R.array.databinding_nba)
            }
            2 ->{
                array = resources.getStringArray(R.array.databinding_menu)
            }
            3 ->{
                array = resources.getStringArray(R.array.main_menu)
            }
            4 ->{
                array = resources.getStringArray(R.array.list_array)
            }
            5 ->{
                array = resources.getStringArray(R.array.plugin_array)
            }
            6 ->{
                array = resources.getStringArray(R.array.plugin_demo)
            }
            else ->{
                array = resources.getStringArray(R.array.architecture)
            }
        }
        mPresenter.fetchList(array?: Array(1){""})
        currentIndex++
    }

    override fun onLoadMore() {

    }

}