package com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.fragments

import android.support.v4.app.Fragment
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.fragment.BaseFragment
import com.example.llcgs.android_kotlin.base.presenter.BasePresenter
import com.example.llcgs.android_kotlin.base.presenter.SuperPresenter

/**
 * com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.fragments.MasterFragment
 * @author liulongchao
 * @since 2017/12/1
 */
class MasterFragment : BaseFragment<SuperPresenter>() {

    override fun getLayoutId() = R.layout.activity_share_fragment_master

    override fun createPresenter() = BasePresenter<Any>()


}