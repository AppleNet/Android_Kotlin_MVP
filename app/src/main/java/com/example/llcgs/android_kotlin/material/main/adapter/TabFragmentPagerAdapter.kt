package com.example.llcgs.android_kotlin.material.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * com.example.llcgs.android_kotlin.material.main.adapter.TabFragmentPagerAdapter
 * @author liulongchao
 * @since 2017/12/13
 */
class TabFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val mFragmentCreatorList = ArrayList<FragmentCreator>()
    private val mTitleList = ArrayList<String>()

    fun addTab(fragmentCreator: FragmentCreator, title: String) {
        mFragmentCreatorList.add(fragmentCreator)
        mTitleList.add(title)
    }

    override fun getItem(position: Int): Fragment = mFragmentCreatorList[position].createFragment()

    override fun getCount()= mFragmentCreatorList.size

    override fun getPageTitle(position: Int)= mTitleList[position]

    interface FragmentCreator {
        fun createFragment(): Fragment
    }
}