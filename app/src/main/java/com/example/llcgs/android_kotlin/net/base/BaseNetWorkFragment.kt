package com.example.llcgs.android_kotlin.net.base

import com.example.llcgs.android_kotlin.base.fragment.BaseFragment
import com.example.llcgs.android_kotlin.base.presenter.SuperPresenter
import com.example.llcgs.android_kotlin.base.rx.exception.ObtainException

/**
 * com.example.llcgs.android_kotlin.material.base.BaseMaterialFragment
 * @author liulongchao
 * @since 2017/12/14
 */
abstract class BaseNetWorkFragment<P : SuperPresenter>: BaseFragment<P>(),
    BaseNetWorkView {

    override fun onObtainFail(exception: ObtainException) {

    }
}