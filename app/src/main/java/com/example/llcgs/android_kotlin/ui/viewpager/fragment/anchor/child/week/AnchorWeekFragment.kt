package com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.week;

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.fragment.BaseFragment
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.week.presenter.impl.AnchorWeekPresenter

/**
 * com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.day.AnchorDayFragment
 * @author liulongchao
 * @since 2018/12/25
 */
class AnchorWeekFragment: BaseFragment<AnchorWeekPresenter>() {

    override fun getLayoutId(): Int = R.layout.fragment_anchor_week

    override fun createPresenter()= AnchorWeekPresenter ()

    override fun initViews() {
    }

    override fun initData() {
    }


}
