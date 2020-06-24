package com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor;

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.ui.viewpager.adapter.ChildViewPagerAdapter
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.all.AnchorAllFragment
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.day.AnchorDayFragment
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.week.AnchorWeekFragment
import kotlinx.android.synthetic.main.fragment_anchor.*

/**
 * com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.AnchorFragment
 * @author liulongchao
 * @since 2018/12/25
 */
class AnchorFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_anchor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPagerAndTabLayout()
    }

    private fun initViewPagerAndTabLayout(){
        //
        val mChildViewPagerAdapter = ChildViewPagerAdapter(activity!!.supportFragmentManager)
        mChildViewPagerAdapter.addTab(object : ChildViewPagerAdapter.FragmentCreator{
            override fun createFragment(): Fragment = AnchorDayFragment()
        }, "日榜")
        mChildViewPagerAdapter.addTab(object : ChildViewPagerAdapter.FragmentCreator{
            override fun createFragment(): Fragment = AnchorWeekFragment()
        }, "周榜")
        mChildViewPagerAdapter.addTab(object : ChildViewPagerAdapter.FragmentCreator{
            override fun createFragment(): Fragment = AnchorAllFragment()
        }, "总榜")

        subViewPager.adapter = mChildViewPagerAdapter
        subTabLayout.setupWithViewPager(subViewPager)
    }



}
