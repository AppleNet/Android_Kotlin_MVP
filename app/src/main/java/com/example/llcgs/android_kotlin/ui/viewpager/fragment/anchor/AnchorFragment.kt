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
import com.example.llcgs.android_kotlin.ui.viewpager.transformer.ScaleInTransformer
import com.example.llcgs.android_kotlin.utils.log.logD
import kotlinx.android.synthetic.main.fragment_anchor.*

/**
 * com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.AnchorFragment
 * @author liulongchao
 * @since 2018/12/25
 */
class AnchorFragment : Fragment() {

    // 标记当前 Fragment 是否彻底可见
    private var isFragmentShow: Boolean = false

    // 标记当前 View 是否创建完成
    private var isViewCreate: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_anchor, container, false)
        initViewPagerAndTabLayout()
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreate = true
        loadData()
    }

    /**
     *  判断Fragment是否可见
     *
     * @param isVisibleToUser
     * */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            isFragmentShow = true
            loadData()
        } else {
            isFragmentShow = false
        }
    }

    private fun initViewPagerAndTabLayout() {
        //
        val mChildViewPagerAdapter = ChildViewPagerAdapter(activity!!.supportFragmentManager)
        mChildViewPagerAdapter.addTab(object : ChildViewPagerAdapter.FragmentCreator {
            override fun createFragment(): Fragment = AnchorDayFragment()
        }, "日榜")
        mChildViewPagerAdapter.addTab(object : ChildViewPagerAdapter.FragmentCreator {
            override fun createFragment(): Fragment = AnchorWeekFragment()
        }, "周榜")
        mChildViewPagerAdapter.addTab(object : ChildViewPagerAdapter.FragmentCreator {
            override fun createFragment(): Fragment = AnchorAllFragment()
        }, "总榜")

        subViewPager.adapter = mChildViewPagerAdapter
        subViewPager.pageMargin = 20
        subViewPager.offscreenPageLimit = 3
        subViewPager.setPageTransformer(true, ScaleInTransformer())
        subTabLayout.setupWithViewPager(subViewPager)

    }

    /**
     *  加载数据
     * */
    private fun loadData() {
        if (isFragmentShow && isViewCreate) {
            "数据加载。。。。".logD()
            isFragmentShow = false
            isViewCreate = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isFragmentShow = false
        isViewCreate = false
    }

}
