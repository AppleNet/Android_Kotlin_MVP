package com.example.llcgs.android_kotlin.algorithms.fast.presenter

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsPresenter

/**
 * com.example.llcgs.android_kotlin.algorithms.fast.presenter.IFastSortPresenter
 * @author liulongchao
 * @since 2018/4/2
 */
interface IFastSortPresenter: BaseAlgorithmsPresenter {

    fun fastSort(array: Array<Int>)
}