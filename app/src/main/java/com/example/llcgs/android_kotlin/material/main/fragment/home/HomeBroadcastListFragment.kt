package com.example.llcgs.android_kotlin.material.main.fragment.home

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialFragment
import com.example.llcgs.android_kotlin.material.detail.DetailActivity
import com.example.llcgs.android_kotlin.material.main.fragment.home.adapter.BroadListAdapter
import com.example.llcgs.android_kotlin.material.main.fragment.home.bean.BroadListContent
import com.example.llcgs.android_kotlin.material.main.fragment.home.presenter.IHomeBroadcastListPresenter
import com.example.llcgs.android_kotlin.material.main.fragment.home.presenter.impl.HomeBroadcastListPresenter
import com.example.llcgs.android_kotlin.material.main.fragment.home.view.HomeBroadcastListView
import kotlinx.android.synthetic.main.fragment_home_broadcastlist.*
import java.util.ArrayList

/**
 * com.example.llcgs.android_kotlin.material.main.fragment.home.HomeBroadcastListFragment
 * @author liulongchao
 * @since 2017/12/13
 */
class HomeBroadcastListFragment: BaseMaterialFragment<IHomeBroadcastListPresenter>(), SwipeRefreshLayout.OnRefreshListener, HomeBroadcastListView, (BroadListContent, View, Int) -> Unit {

    private lateinit var adapter: BroadListAdapter

    override fun getLayoutId() = R.layout.fragment_home_broadcastlist

    override fun createPresenter() = HomeBroadcastListPresenter(this)

    override fun initViews() {
        adapter = BroadListAdapter()
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        swipeRefreshLayout.setOnRefreshListener(this)

        adapter.setOpenBroadCast(this)
    }

    override fun initData() {
        // 获取数据
        mPresenter.getData()
    }

    override fun onGetData(list: List<BroadListContent>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(list)
    }

    override fun invoke(item: BroadListContent, view: View, position: Int) {
        // Intent
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("show_send_comment", true)
        intent.putExtra("title", "Detail")
        intent.putExtra("broadcast", item)
        intent.putExtra("id", position)
        // 添加转场动画
        val list = ArrayList<Pair<View, String>>()
        list.add(Pair.create(view, view.transitionName))
        val appbar = activity?.findViewById<View>(R.id.appBarLayout)
        if (appbar != null) {
            list.add(Pair.create(appbar, "id"))
        }
        val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as Activity, *list.toTypedArray()).toBundle()
        ActivityCompat.startActivity(activity!!, intent, bundle)
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = true
        adapter.data.clear()
        mPresenter.getData()
    }
}