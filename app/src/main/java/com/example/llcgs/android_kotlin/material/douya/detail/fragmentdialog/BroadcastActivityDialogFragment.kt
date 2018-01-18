package com.example.llcgs.android_kotlin.material.douya.detail.fragmentdialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatDialog
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.douya.detail.fragment.LikeFragment
import com.example.llcgs.android_kotlin.material.douya.detail.fragment.ReBroadcastFragment
import com.example.llcgs.android_kotlin.material.douya.detail.fragmentdialog.adapter.TabFragmentPagerAdapter
import com.example.llcgs.android_kotlin.material.douya.main.fragment.home.bean.BroadListContent
import kotlinx.android.synthetic.main.view_alert_dialog_button_material.*
import kotlinx.android.synthetic.main.view_broadcast_activity_dialog_fragment.*

/**
 * com.example.llcgs.android_kotlin.material.detail.fragmentdialog.BroadcastActivityDialogFragment
 * @author liulongchao
 * @since 2017/12/18
 */
class BroadcastActivityDialogFragment: AppCompatDialogFragment() {

    private lateinit var adapter: com.example.llcgs.android_kotlin.material.douya.detail.fragmentdialog.adapter.TabFragmentPagerAdapter
    private var broadcast: com.example.llcgs.android_kotlin.material.douya.main.fragment.home.bean.BroadListContent? = null
    private val likeFragment = com.example.llcgs.android_kotlin.material.douya.detail.fragment.LikeFragment()
    private val reBroadcastFragment = com.example.llcgs.android_kotlin.material.douya.detail.fragment.ReBroadcastFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        broadcast = arguments?.getParcelable("broadcast")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as AppCompatDialog
        dialog.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.view_broadcast_activity_dialog_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews(){
        adapter = com.example.llcgs.android_kotlin.material.douya.detail.fragmentdialog.adapter.TabFragmentPagerAdapter(this.childFragmentManager)
        adapter.addTab(object : com.example.llcgs.android_kotlin.material.douya.detail.fragmentdialog.adapter.TabFragmentPagerAdapter.FragmentCreator{
            override fun createFragment() = likeFragment
        }, "")

        adapter.addTab(object : com.example.llcgs.android_kotlin.material.douya.detail.fragmentdialog.adapter.TabFragmentPagerAdapter.FragmentCreator{
            override fun createFragment() = reBroadcastFragment
        }, "")
        updateTabTitle()
        viewPager.offscreenPageLimit = 0
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        button1.text = "确定"
        button1.visibility = View.VISIBLE
        button1.setOnClickListener{
            dismiss()
        }

        button2.visibility = View.GONE
        button3.visibility = View.GONE

        likeFragment.setListener{
            adapter.setPageTitle(tabLayout, 0, getTabTitle(it, R.string.broadcast_likers_title_format, R.string.broadcast_likers_title_empty))
        }
        reBroadcastFragment.setRebroadcastListener{
            adapter.setPageTitle(tabLayout, 1, getTabTitle(it, R.string.broadcast_title_format, R.string.broadcast_title_empty))
        }
    }

    private fun updateTabTitle(){
        adapter.setPageTitle(tabLayout, 0, getTabTitle(0, R.string.broadcast_likers_title_format, R.string.broadcast_likers_title_empty))
        adapter.setPageTitle(tabLayout, 1, getTabTitle(0, R.string.broadcast_title_format, R.string.broadcast_title_empty))
    }

    private fun getTabTitle(count:Int, formatResId:Int, emptyResId: Int): String =
            if (count > 0) getString(formatResId, count) else getString(emptyResId)
}