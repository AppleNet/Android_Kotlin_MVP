package com.example.llcgs.android_kotlin.ui.viewpager;

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.ui.viewpager.adapter.ParentViewPagerAdapter
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.AnchorFragment
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor.KingAnchorFragment
import com.example.llcgs.android_kotlin.ui.viewpager.presenter.impl.ViewPagerPresenter
import com.example.llcgs.android_kotlin.ui.viewpager.view.ViewPagerView
import kotlinx.android.synthetic.main.activity_viewpager.*

/**
 * com.example.llcgs.android_kotlin.ui.viewpager.ViewPagerActivity
 * @author liulongchao
 * @since 2018/12/25
 */
class ViewPagerActivity : BaseActivity<ViewPagerView, ViewPagerPresenter>(){


    override fun createPresenter()= ViewPagerPresenter ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)
        initViewPagerAndTabLayout()
    }

    private fun initViewPagerAndTabLayout(){

        val mViewPagerAdapterAdapter = ParentViewPagerAdapter(supportFragmentManager)
        mViewPagerAdapterAdapter.addTab(object : ParentViewPagerAdapter.FragmentCreator{
            override fun createFragment(): Fragment = AnchorFragment()
        }, "主播榜")
        mViewPagerAdapterAdapter.addTab(object : ParentViewPagerAdapter.FragmentCreator{
            override fun createFragment(): Fragment = KingAnchorFragment()
        }, "金主榜")
        viewPager.adapter = mViewPagerAdapterAdapter

        // tab的字体选择器,默认黑色,选择时红色
        // tabLayout.setTabTextColors(Color.BLACK, Color.RED)
        tabLayout.setupWithViewPager(viewPager)
    }
}
