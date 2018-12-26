package com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.all;

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.fragment.BaseFragment
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.all.presenter.impl.AnchorAllPresenter
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.all.view.AnchorAllView

/**
 * com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.day.AnchorDayFragment
 * @author liulongchao
 * @since 2018/12/25
 */
class AnchorAllFragment: BaseFragment<AnchorAllPresenter>(), AnchorAllView {

    override fun getLayoutId(): Int = R.layout.fragment_anchor_all

    override fun createPresenter()= AnchorAllPresenter()

    override fun initViews() {
    }

    override fun initData() {
    }


}
