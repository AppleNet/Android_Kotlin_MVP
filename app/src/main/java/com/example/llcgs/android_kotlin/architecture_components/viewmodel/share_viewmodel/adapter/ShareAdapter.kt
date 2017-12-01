package com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * com.example.llcgs.android_kotlin.mvvm.converters.adapter.ConvertersAdapter
 * @author liulongchao
 * @since 2017/11/24
 */
class ShareAdapter<T : Fragment>(fm: FragmentManager, val fragment: ArrayList<T>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? = if (fragment.isEmpty()) null else fragment[position]

    override fun getCount(): Int = if (fragment.isEmpty()) 0 else fragment.size

}