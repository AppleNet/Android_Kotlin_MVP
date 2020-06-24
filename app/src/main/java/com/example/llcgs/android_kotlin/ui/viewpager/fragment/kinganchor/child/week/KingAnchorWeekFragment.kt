package com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor.child.week;

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.fragment.BaseFragment
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor.child.week.presenter.IKingAnchorWeekPresenter
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor.child.week.presenter.impl.KingAnchorWeekPresenter

/**
 * com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.day.AnchorDayFragment
 * @author liulongchao
 * @since 2018/12/25
 */
class KingAnchorWeekFragment: BaseFragment<IKingAnchorWeekPresenter>() {

    override fun getLayoutId(): Int = R.layout.fragment_king_anchor_week

    override fun createPresenter(): IKingAnchorWeekPresenter = KingAnchorWeekPresenter()

    override fun initViews() {
    }

    override fun initData() {
    }

}
