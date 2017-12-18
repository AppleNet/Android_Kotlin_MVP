package com.example.llcgs.android_kotlin.material.detail.fragmentdialog.adapter

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * com.example.llcgs.android_kotlin.material.detail.fragmentdialog.adapter.TabFragmentPagerAdapter
 * @author liulongchao
 * @since 2017/12/18
 */
class TabFragmentPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    private var mFragmentCreatorList = ArrayList<FragmentCreator>()
    private var mTitleList = ArrayList<String>()

    override fun getItem(position: Int)= mFragmentCreatorList[position].createFragment()

    override fun getCount()= mFragmentCreatorList.size

    override fun getPageTitle(position: Int): CharSequence? = mTitleList[position]

    fun addTab(fragmentCreator: FragmentCreator, title:String){
        mFragmentCreatorList.add(fragmentCreator)
        mTitleList.add(title)
    }

    fun setPageTitle(tabLayout: TabLayout, position: Int, title: String){
        mTitleList.set(position, title)
        if (position < tabLayout.tabCount){
            tabLayout.getTabAt(position)?.text = title
        }
    }

    interface FragmentCreator {
        fun createFragment(): Fragment
    }
}