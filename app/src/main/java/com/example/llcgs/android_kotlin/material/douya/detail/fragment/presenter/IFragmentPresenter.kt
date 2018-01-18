package com.example.llcgs.android_kotlin.material.douya.detail.fragment.presenter

import com.example.llcgs.android_kotlin.material.base.BaseMaterialPresenter

/**
 * com.example.llcgs.android_kotlin.material.detail.fragment.presenter.IFragmentPresenter
 * @author liulongchao
 * @since 2017/12/22
 */
interface IFragmentPresenter: BaseMaterialPresenter {

    fun loadLikers()

    fun loadRebroadcast()
}