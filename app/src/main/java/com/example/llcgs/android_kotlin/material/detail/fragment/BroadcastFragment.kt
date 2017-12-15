package com.example.llcgs.android_kotlin.material.detail.fragment

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.SharedElementCallback
import android.support.v4.view.ViewCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.transition.Explode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.detail.fragment.adapter.BroadcastAdapter
import com.example.llcgs.android_kotlin.material.detail.fragment.view.CustomTabsHelperFragment
import com.example.llcgs.android_kotlin.material.detail.fragment.view.NoChangeAnimationItemAnimator
import com.example.llcgs.android_kotlin.material.main.fragment.home.bean.BroadListContent
import kotlinx.android.synthetic.main.broadcast_fragment.*

/**
 * com.example.llcgs.android_kotlin.material.detail.fragment.BroadcastFragment
 * @author liulongchao
 * @since 2017/12/14
 */
class BroadcastFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var title: String = ""
    private var broadcastId = 0
    private lateinit var broadcast: BroadListContent
    private lateinit var adapter: BroadcastAdapter
    private val headerView: View by lazy { LayoutInflater.from(activity).inflate(R.layout.view_broadcast_layout, null) }
    private lateinit var holder: HeaderViewHolder

    companion object {

        fun setData(broadcastId: Int, broadcast: BroadListContent, showSendComment: Boolean, title: String): BroadcastFragment {
            val broadcastFragment = BroadcastFragment()
            var arguments = broadcastFragment.arguments
            if (arguments == null) {
                arguments = Bundle()
                broadcastFragment.arguments = arguments
            }
            arguments.putInt("broadcastId", broadcastId)
            arguments.putBoolean("showSendComment", showSendComment)
            arguments.putString("title", title)
            arguments.putParcelable("broadcast", broadcast)
            return broadcastFragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        broadcast = bundle?.getParcelable("broadcast") as BroadListContent
        broadcastId = bundle.getInt("broadcastId")
        val showComment = bundle.getBoolean("showSendComment")
        title = bundle.getString("title")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.broadcast_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fragmentManager = activity?.supportFragmentManager
        var fragment: Fragment? = fragmentManager?.findFragmentByTag(CustomTabsHelperFragment::class.java.name)
        if (fragment == null) {
            fragment = CustomTabsHelperFragment()
            fragmentManager?.beginTransaction()?.add(fragment, CustomTabsHelperFragment::class.java.name)?.commit()
        }

        activity?.title = title
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        ViewCompat.setTransitionName(shared, broadcastId.toString())
        ActivityCompat.setEnterSharedElementCallback(activity as AppCompatActivity, object : SharedElementCallback(){
            override fun onSharedElementEnd(sharedElementNames: MutableList<String>?, sharedElements: MutableList<View>?, sharedElementSnapshots: MutableList<View>?) {
                recyclerView.scrollToPosition(0)
            }
        })

        swipeRefreshLayout.setOnRefreshListener(this)

        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = NoChangeAnimationItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(activity)

        holder = HeaderViewHolder(headerView)
        //initData()
        adapter = BroadcastAdapter()
        adapter.addHeaderView(headerView)

        recyclerView.adapter = adapter

        progress.visibility = View.VISIBLE
        activity?.window?.decorView?.postDelayed({
            Toast.makeText(activity, "这条广播暂不支持回应", Toast.LENGTH_SHORT).show()
            progress.visibility = View.GONE
        }, 400)

        //
        comment.isEnabled = false
        send.isEnabled = false
        comment.hint = "这条广播暂不支持回应"

        val explode = Explode()
                .excludeTarget(android.R.id.statusBarBackground, true)
                .excludeTarget(android.R.id.navigationBarBackground, true)
        activity?.window?.enterTransition = explode
        activity?.window?.returnTransition = explode

        appBarWrapper.transitionName = "id"
        ActivityCompat.startPostponedEnterTransition(activity as AppCompatActivity)
    }

    private fun initData(){
        Glide.with(activity).load(broadcast.avatar).into(holder.circleImage)
        holder.name.text = broadcast.name
        holder.content.text = broadcast.content
        holder.time.text = broadcast.time
        Glide.with(activity).load(broadcast.attachmentImage).into(holder.image)
        holder.title.text = broadcast.attachmentTitle
        holder.des.text = broadcast.attachmentDes

    }

    override fun onRefresh() {

    }

    class HeaderViewHolder(private val headerView: View){

        val name = headerView.findViewById<TextView>(R.id.name)
        val time = headerView.findViewById<TextView>(R.id.time_action)
        val content = headerView.findViewById<TextView>(R.id.text)
        val circleImage = headerView.findViewById<ImageView>(R.id.avatar)

        val title = headerView.findViewById<TextView>(R.id.attachment_title)
        val des = headerView.findViewById<TextView>(R.id.attachment_description)
        val image = headerView.findViewById<ImageView>(R.id.attachment_image)

    }
}