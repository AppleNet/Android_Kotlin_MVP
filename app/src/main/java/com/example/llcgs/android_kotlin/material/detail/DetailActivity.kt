package com.example.llcgs.android_kotlin.material.detail

import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.SharedElementCallback
import android.support.v4.view.ViewCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_broadcast.*

/**
 * com.example.llcgs.android_kotlin.material.detail.DetailActivity
 * @author liulongchao
 * @since 2017/12/14
 */
class DetailActivity : BaseDetailActivity<DetailPresenter>(), OnRefreshListener, DetailView, View.OnClickListener {

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
        this@DetailActivity.setTitle(title)

        Glide.with(this).load(broadcast.avatar).into(holder.circleImageView)
        holder.name.text = broadcast.name
        holder.time.text = broadcast.time
        holder.content.text = broadcast.content

        Glide.with(this).load(broadcast.attachmentImage).into(holder.attachmentImage)
        holder.title.text = broadcast.attachmentTitle
        holder.des.text = broadcast.attachmentDes
        holder.number.visibility = View.VISIBLE
        holder.number.setOnClickListener(this)

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

    class HeaderViewHolder(view: View) {
        val circleImageView: CircleImageView = view.findViewById(R.id.avatar)
        val name: TextView = view.findViewById(R.id.name)
        val time: TextView = view.findViewById(R.id.time_action)
        val content: TextView = view.findViewById(R.id.text)
        val number: AppCompatTextView = view.findViewById(R.id.rebroadcastNumber)

        val attachmentImage: ImageView = view.findViewById(R.id.attachment_image)
        val title: TextView = view.findViewById(R.id.attachment_title)
        val des: TextView = view.findViewById(R.id.attachment_description)
    }

}