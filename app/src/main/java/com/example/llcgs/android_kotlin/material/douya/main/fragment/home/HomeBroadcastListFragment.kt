package com.example.llcgs.android_kotlin.material.douya.main.fragment.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialFragment
import com.example.llcgs.android_kotlin.material.douya.main.fragment.home.adapter.BroadListAdapter
import com.example.llcgs.android_kotlin.material.douya.main.fragment.home.presenter.IHomeBroadcastListPresenter
import com.example.llcgs.android_kotlin.material.douya.main.fragment.home.presenter.impl.HomeBroadcastListPresenter
import com.example.llcgs.android_kotlin.material.douya.main.fragment.home.view.HomeBroadcastListView
import com.example.llcgs.android_kotlin.material.douya.webview.MaterialWebViewActivity
import kotlinx.android.synthetic.main.fragment_home_broadcastlist.*

/**
 * com.example.llcgs.android_kotlin.material.main.fragment.home.HomeBroadcastListFragment
 * @author liulongchao
 * @since 2017/12/13
 */
class HomeBroadcastListFragment: BaseMaterialFragment<IHomeBroadcastListPresenter>(), SwipeRefreshLayout.OnRefreshListener, HomeBroadcastListView, (com.example.llcgs.android_kotlin.material.douya.main.fragment.home.bean.BroadListContent, View, Int) -> Unit, View.OnClickListener {

    private lateinit var adapter: BroadListAdapter
    private lateinit var item: com.example.llcgs.android_kotlin.material.douya.main.fragment.home.bean.BroadListContent
    private var position: Int = 0

    override fun getLayoutId() = R.layout.fragment_home_broadcastlist

    override fun createPresenter() = HomeBroadcastListPresenter(this)

    override fun initViews() {
        adapter = BroadListAdapter()
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.setOnRefreshListener(this)

        send.setOnClickListener(this)

        adapter.setOpenBroadCast(this)
    }

    override fun initData() {
        // 获取数据
        mPresenter.getData()
    }

    override fun onGetData(list: List<com.example.llcgs.android_kotlin.material.douya.main.fragment.home.bean.BroadListContent>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(list)
    }

    override fun onGetBundle(bundle: Bundle) {
        ActivityCompat.startActivity(activity!!, Intent(activity, com.example.llcgs.android_kotlin.material.douya.detail.MaterialDetailActivity::class.java).apply {
            putExtra("showSendComment", true)
            putExtra("title", "豆芽")
            putExtra("broadcast", item)
            putExtra("broadcastId", "broadcast-$position")
        }, bundle)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.send ->{
                startActivity(Intent(activity, MaterialWebViewActivity::class.java).apply {
                    putExtra("EXTRA_URL", "https://www.douban.com/#isay-cont")
                })
            }
        }
    }

    override fun invoke(item: com.example.llcgs.android_kotlin.material.douya.main.fragment.home.bean.BroadListContent, view: View, position: Int) {
        this.item = item
        this.position = position
        mPresenter.addTransitionAnimation(activity as Activity, view)
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = true
        adapter.data.clear()
        mPresenter.getData()
    }
}