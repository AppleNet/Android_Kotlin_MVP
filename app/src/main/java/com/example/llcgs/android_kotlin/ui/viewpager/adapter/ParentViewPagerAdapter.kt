package com.example.llcgs.android_kotlin.ui.viewpager.adapter

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import java.util.ArrayList

class ParentViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val mFragmentList: ArrayList<FragmentCreator> = ArrayList()
    private val mActivityList: ArrayList<ActivityCreator> = ArrayList()
    private val mPageTitleList: ArrayList<String> = ArrayList()

    fun addTab(fragmentCreator: FragmentCreator, title: String) {
        mFragmentList.add(fragmentCreator)
        mPageTitleList.add(title)
    }

    fun addTab(activityCreator: ActivityCreator, title: String){
        mActivityList.add(activityCreator)
        mPageTitleList.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position].createFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mPageTitleList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    interface FragmentCreator {
        fun createFragment(): Fragment
    }

    interface ActivityCreator{
        fun createActivity(): Activity
    }

    interface BaseCreator{}
}
