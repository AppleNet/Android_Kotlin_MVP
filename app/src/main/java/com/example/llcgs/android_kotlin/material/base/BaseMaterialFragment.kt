package com.example.llcgs.android_kotlin.material.base

import com.example.llcgs.android_kotlin.base.fragment.BaseFragment
import com.example.llcgs.android_kotlin.base.presenter.SuperPresenter
import com.example.llcgs.android_kotlin.base.rx.exception.ObtainException

/**
 * com.example.llcgs.android_kotlin.material.base.BaseMaterialFragment
 * @author liulongchao
 * @since 2017/12/14
 */
abstract class BaseMaterialFragment<P : SuperPresenter>: BaseFragment<P>(), BaseMaterialView {

    override fun onObtainFail(exception: ObtainException) {

    }
}