package com.example.llcgs.android_kotlin.design_pattern.simplefactory.presenter;

import com.example.llcgs.android_kotlin.base.presenter.SuperPresenter

/**
 * com.example.llcgs.android_kotlin.design_pattern.simplefactory.presenter.ISimpleFactoryPresenter
 * @author liulongchao
 * @since 2019/2/18
 */
interface ISimpleFactoryPresenter: SuperPresenter {
    fun login(type: String)
}
