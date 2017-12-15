package com.example.llcgs.android_kotlin.material.detail

import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.SharedElementCallback
import android.support.v4.view.ViewCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.detail.adapter.BroadcastAdapter
import com.example.llcgs.android_kotlin.material.detail.adapter.LoadMoreAdapter
import com.example.llcgs.android_kotlin.material.detail.view.NoChangeAnimationItemAnimator
import com.example.llcgs.android_kotlin.material.detail.presenter.impl.DetailPresenter
import com.example.llcgs.android_kotlin.material.main.fragment.home.adapter.BroadListAdapter
import com.example.llcgs.android_kotlin.material.main.fragment.home.bean.BroadListContent
import com.example.llcgs.android_kotlin.utils.TransitionUtils
import kotlinx.android.synthetic.main.fragment_broadcast.*

/**
 * com.example.llcgs.android_kotlin.material.detail.DetailActivity
 * @author liulongchao
 * @since 2017/12/14
 */
class DetailActivity : BaseDetailActivity<DetailPresenter>(), SwipeRefreshLayout.OnRefreshListener {

    private var title: String = ""
    private var broadcastId: String = ""
    private var showComment: Boolean = false
    private lateinit var broadcast: BroadListContent
    private lateinit var adapterComment: BroadcastAdapter
    private lateinit var adapterContent: BroadListAdapter
    private lateinit var adapter: LoadMoreAdapter

    override fun createPresenter()= DetailPresenter()

    override fun getLayoutId() = R.layout.fragment_broadcast

    override fun initViews() {
        setSupportActionBar(toolbar)
        ViewCompat.setTransitionName(shared, broadcastId)
        ActivityCompat.setEnterSharedElementCallback(this, object : SharedElementCallback(){
            override fun onSharedElementEnd(sharedElementNames: MutableList<String>?,
                                            sharedElements: MutableList<View>?,
                                            sharedElementSnapshots: MutableList<View>?) {
                recyclerView.scrollToPosition(0)
            }
        })

        swipeRefreshLayout.setOnRefreshListener(this)

        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = NoChangeAnimationItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 初始化适配器
        adapterComment = BroadcastAdapter()
        adapterContent = BroadListAdapter()

        // 添加数据到adapter
        adapterContent.addData(broadcast)
        adapterComment.addData(resources?.getStringArray(R.array.databinding_nba)?.toMutableList()?: ArrayList<String>())

        adapter = LoadMoreAdapter(R.layout.view_load_more_item, adapterContent, adapterComment)
        recyclerView.adapter = adapter

        progress.visibility = View.VISIBLE
        TransitionUtils.postActivityAfterTransition(this) {
            //Toast.makeText(activity, "这条广播暂不支持回应", Toast.LENGTH_SHORT).show()
            progress.visibility = View.GONE
        }

        TransitionUtils.setActivityEnterReturnExplode(this)
        TransitionUtils.setupActivityTransitionOnActivityCreated(this)
    }

    override fun initData(){
        this@DetailActivity.setTitle(title)
    }

    override fun onGetIntentData(intent: Intent) {
        broadcastId = intent.getStringExtra("broadcastId")
        broadcast = intent.getParcelableExtra<BroadListContent>("broadcast")
        showComment = intent.getBooleanExtra("showSendComment", false)
        title = intent.getStringExtra("title")
    }

    override fun onRefresh() {

    }

}