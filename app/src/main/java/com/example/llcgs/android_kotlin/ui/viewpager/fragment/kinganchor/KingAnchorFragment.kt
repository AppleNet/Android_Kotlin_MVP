package com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor;

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.ui.viewpager.adapter.ChildViewPagerAdapter
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor.child.all.KingAnchorAllFragment
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor.child.day.KingAnchorDayFragment
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor.child.week.KingAnchorWeekFragment
import kotlinx.android.synthetic.main.fragment_king_anchor.*

/**
 * com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor.KingAnchorFragment
 * @author liulongchao
 * @since 2018/12/25
 */
class KingAnchorFragment: Fragment() {
    //

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_king_anchor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPagerAndTabLayout()
    }

    private fun initViewPagerAndTabLayout(){
        val mChildViewPagerAdapter = ChildViewPagerAdapter(activity!!.supportFragmentManager)
        mChildViewPagerAdapter.addTab(object : ChildViewPagerAdapter.FragmentCreator{
            override fun createFragment(): Fragment = KingAnchorDayFragment()
        }, "日榜")
        mChildViewPagerAdapter.addTab(object : ChildViewPagerAdapter.FragmentCreator{
            override fun createFragment(): Fragment = KingAnchorWeekFragment()
        }, "周榜")
        mChildViewPagerAdapter.addTab(object : ChildViewPagerAdapter.FragmentCreator{
            override fun createFragment(): Fragment = KingAnchorAllFragment()
        }, "总榜")

        subKingViewPager.adapter = mChildViewPagerAdapter
        subKingTabLayout.setupWithViewPager(subKingViewPager)
    }
}
