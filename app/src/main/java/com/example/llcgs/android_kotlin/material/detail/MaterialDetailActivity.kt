package com.example.llcgs.android_kotlin.material.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.SharedElementCallback
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.detail.adapter.BroadcastAdapter
import com.example.llcgs.android_kotlin.material.detail.fragmentdialog.BroadcastActivityDialogFragment
import com.example.llcgs.android_kotlin.material.detail.view.NoChangeAnimationItemAnimator
import com.example.llcgs.android_kotlin.material.detail.presenter.impl.DetailPresenter
import com.example.llcgs.android_kotlin.material.detail.view.DetailView
import com.example.llcgs.android_kotlin.material.main.fragment.home.bean.BroadListContent
import com.example.llcgs.android_kotlin.utils.TransitionUtils
import com.example.llcgs.android_kotlin.utils.widget.refreshlayout.callback.OnRefreshListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_broadcast.*
import kotlinx.android.synthetic.main.view_broadcast_layout.*

/**
 * com.example.llcgs.android_kotlin.material.detail.MaterialDetailActivity
 * @author liulongchao
 * @since 2017/12/14
 */
class MaterialDetailActivity : BaseDetailActivity<DetailPresenter>(), OnRefreshListener, DetailView, View.OnClickListener {

    private var title: String = ""
    private var broadcastId: String = ""
    private var showComment: Boolean = false
    private lateinit var broadcast: BroadListContent
    private lateinit var adapter: BroadcastAdapter

    private val headerView: View by lazy { LayoutInflater.from(this).inflate(R.layout.view_home_broadcast_list_recyclerview_item, null) }
    private lateinit var holder: HeaderViewHolder

    override fun createPresenter() = DetailPresenter(this)

    override fun getLayoutId() = R.layout.fragment_broadcast

    override fun initViews() {
        setSupportActionBar(toolbar)
        ViewCompat.setTransitionName(shared, broadcastId)
        ActivityCompat.setEnterSharedElementCallback(this, object : SharedElementCallback() {
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
        adapter = BroadcastAdapter()
        holder = HeaderViewHolder(headerView)
        adapter.addHeaderView(headerView)
        recyclerView.adapter = adapter

        progress.visibility = View.VISIBLE
        TransitionUtils.postActivityAfterTransition(this) {
            progress.visibility = View.GONE
        }

        TransitionUtils.setActivityEnterReturnExplode(this)
        TransitionUtils.setupActivityTransitionOnActivityCreated(this)
    }

    override fun initData() {
        this@MaterialDetailActivity.setTitle(title)

        Glide.with(this).load(broadcast.avatar).into(holder.avatar)
        holder.name.text = broadcast.name
        holder.time_action.text = broadcast.time
        holder.text.text = broadcast.content

        Glide.with(this).load(broadcast.attachmentImage).into(holder.attachment_image)
        holder.attachment_title.text = broadcast.attachmentTitle
        holder.attachment_description.text = broadcast.attachmentDes
        holder.rebroadcastNumber.visibility = View.VISIBLE
        holder.rebroadcastNumber.setOnClickListener(this)

        swipeRefreshLayout.autoRefresh()
        mPresenter.loadComments()
    }

    override fun onGetIntentData(intent: Intent) {
        broadcastId = intent.getStringExtra("broadcastId")
        broadcast = intent.getParcelableExtra<BroadListContent>("broadcast")
        showComment = intent.getBooleanExtra("showSendComment", false)
        title = intent.getStringExtra("title")
    }

    override fun onRefresh() {
        mPresenter.loadComments()
    }

    override fun onLoadMore() {
        mPresenter.loadComments()
    }

    override fun onGetComments(list: List<String>) {
        if (swipeRefreshLayout.isRefreshing){
            swipeRefreshLayout.stopRefreshLayout()
            adapter.data.clear()
            adapter.setNewData(list)
        }else if (swipeRefreshLayout.isLoadingMore){
            swipeRefreshLayout.stopRefreshLayout()
            adapter.addData(list)
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.rebroadcastNumber ->{
                // 查看点赞和转播人数
                val fragment = BroadcastActivityDialogFragment()
                fragment.arguments?.putParcelable("broadcast", broadcast)
                fragment.show(supportFragmentManager, null)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.broadcast, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                ActivityCompat.finishAfterTransition(this)
            }
            R.id.action_copy_text ->{
                // 复制文本
                val clipData = ClipData.newPlainText(broadcast.attachmentTitle, broadcast.content)
                (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).primaryClip = clipData
            }
            R.id.action_delete ->{
                // 删除广播
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class HeaderViewHolder(override val containerView: View): LayoutContainer
}