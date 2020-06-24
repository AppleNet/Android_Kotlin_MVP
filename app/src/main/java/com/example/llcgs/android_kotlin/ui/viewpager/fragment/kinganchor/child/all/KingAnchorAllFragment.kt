package com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor.child.all;

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.fragment.BaseFragment
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor.child.all.presenter.IKingAnchorAllPresenter
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor.child.all.presenter.impl.KingAnchorAllPresenter

/**
 * com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.child.day.AnchorDayFragment
 * @author liulongchao
 * @since 2018/12/25
 */
class KingAnchorAllFragment: BaseFragment<IKingAnchorAllPresenter>() {

    override fun getLayoutId(): Int = R.layout.fragment_king_anchor_all

    override fun createPresenter(): IKingAnchorAllPresenter = KingAnchorAllPresenter()

    override fun initViews() {
    }

    override fun initData() {
    }


}
