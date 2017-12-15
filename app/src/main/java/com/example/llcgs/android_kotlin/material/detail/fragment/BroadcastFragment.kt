package com.example.llcgs.android_kotlin.material.detail.fragment

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.SharedElementCallback
import android.support.v4.view.ViewCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.transition.ChangeBounds
import android.transition.Explode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.detail.fragment.adapter.BroadcastAdapter
import com.example.llcgs.android_kotlin.material.detail.fragment.adapter.LoadMoreAdapter
import com.example.llcgs.android_kotlin.material.detail.fragment.view.CustomTabsHelperFragment
import com.example.llcgs.android_kotlin.material.detail.fragment.view.NoChangeAnimationItemAnimator
import com.example.llcgs.android_kotlin.material.main.fragment.home.adapter.BroadListAdapter
import com.example.llcgs.android_kotlin.material.main.fragment.home.bean.BroadListContent
import com.example.llcgs.android_kotlin.utils.TransitionUtils
import com.gomejr.myf.core.kotlin.logD
import kotlinx.android.synthetic.main.fragment_broadcast.*

/**
 * com.example.llcgs.android_kotlin.material.detail.fragment.BroadcastFragment
 * @author liulongchao
 * @since 2017/12/14
 */
class BroadcastFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var title: String = ""
    private var broadcastId: String = ""
    private var showComment: Boolean = false
    private lateinit var broadcast: BroadListContent
    private lateinit var adapterComment: BroadcastAdapter
    private lateinit var adapterContent: BroadListAdapter
    private lateinit var adapter: LoadMoreAdapter

    companion object {

        fun setData(broadcastId: String, broadcast: BroadListContent, showSendComment: Boolean, title: String): BroadcastFragment {
            val broadcastFragment = BroadcastFragment()
            var arguments = broadcastFragment.arguments
            if (arguments == null) {
                arguments = Bundle()
                broadcastFragment.arguments = arguments
            }
            arguments.putString("broadcastId", broadcastId)
            arguments.putBoolean("showSendComment", showSendComment)
            arguments.putString("title", title)
            arguments.putParcelable("broadcast", broadcast)
            broadcastFragment.sharedElementEnterTransition = ChangeBounds()
            return broadcastFragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        broadcast = bundle?.getParcelable("broadcast") as BroadListContent
        broadcastId = bundle.getString("broadcastId")
        showComment = bundle.getBoolean("showSendComment")
        title = bundle.getString("title")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_broadcast, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        CustomTabsHelperFragment.attachTo(this)

        activity?.title = title
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        broadcastId.logD()
        ViewCompat.setTransitionName(shared, broadcastId)
        ActivityCompat.setEnterSharedElementCallback(activity as AppCompatActivity, object : SharedElementCallback(){
            override fun onSharedElementEnd(sharedElementNames: MutableList<String>?,
                                            sharedElements: MutableList<View>?,
                                            sharedElementSnapshots: MutableList<View>?) {
                recyclerView.scrollToPosition(0)
            }
        })

        swipeRefreshLayout.setOnRefreshListener(this)

        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = NoChangeAnimationItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // 初始化适配器
        adapterComment = BroadcastAdapter()
        adapterContent = BroadListAdapter()

        // 添加数据到adapter
        adapterContent.addData(broadcast)
        adapterComment.addData(activity?.resources?.getStringArray(R.array.databinding_nba)?.toMutableList()?: ArrayList<String>())

        adapter = LoadMoreAdapter(R.layout.view_load_more_item, adapterContent, adapterComment)
        recyclerView.adapter = adapter

        progress.visibility = View.VISIBLE
        TransitionUtils.postAfterTransition(this) {
            //Toast.makeText(activity, "这条广播暂不支持回应", Toast.LENGTH_SHORT).show()
            progress.visibility = View.GONE
        }

        TransitionUtils.setEnterReturnExplode(this)
        TransitionUtils.setupTransitionOnActivityCreated(this)
    }


    override fun onRefresh() {

    }

}