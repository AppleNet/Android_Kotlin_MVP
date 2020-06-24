package com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor.child.day;

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.fragment.BaseFragment
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor.child.day.presenter.IKingAnchorDayPresenter
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor.child.day.presenter.impl.KingAnchorDayPresenter

/**
 * com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.day.AnchorDayFragment
 * @author liulongchao
 * @since 2018/12/25
 */
class KingAnchorDayFragment: BaseFragment<IKingAnchorDayPresenter>() {

    override fun getLayoutId(): Int = R.layout.fragment_king_anchor_day

    override fun createPresenter(): IKingAnchorDayPresenter = KingAnchorDayPresenter()

    override fun initViews() {
    }

    override fun initData() {
    }


}
