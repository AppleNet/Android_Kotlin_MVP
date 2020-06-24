package com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.day;

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.fragment.BaseFragment
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.day.presenter.impl.AnchorDayPresenter

/**
 * com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.day.AnchorDayFragment
 * @author liulongchao
 * @since 2018/12/25
 */
class AnchorDayFragment: BaseFragment<AnchorDayPresenter>() {

    override fun getLayoutId(): Int = R.layout.fragment_anchor_day

    override fun createPresenter()= AnchorDayPresenter()

    override fun initViews() {

    }

    override fun initData() {

    }

}
