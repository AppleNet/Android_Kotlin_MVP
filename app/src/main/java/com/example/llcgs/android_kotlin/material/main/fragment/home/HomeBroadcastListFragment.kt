package com.example.llcgs.android_kotlin.material.main.fragment.home

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.fragment.BaseFragment
import com.example.llcgs.android_kotlin.material.main.fragment.home.presenter.IHomeBroadcastListPresenter
import com.example.llcgs.android_kotlin.material.main.fragment.home.presenter.impl.HomeBroadcastListPresenter

/**
 * com.example.llcgs.android_kotlin.material.main.fragment.home.HomeBroadcastListFragment
 * @author liulongchao
 * @since 2017/12/13
 */
class HomeBroadcastListFragment: BaseFragment<IHomeBroadcastListPresenter>() {

    override fun getLayoutId() = R.layout.fragment_home_broadcastlist

    override fun createPresenter() = HomeBroadcastListPresenter()

    override fun initViews() {

    }

    override fun initData() {
    }


}