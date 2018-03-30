package com.example.llcgs.android_kotlin.algorithms.select.presenter

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsPresenter

/**
 * com.example.llcgs.android_kotlin.algorithms.select.presenter.ISelectSortPresenter
 * @author liulongchao
 * @since 2018/3/30
 */
interface ISelectSortPresenter: BaseAlgorithmsPresenter {

    fun selectSort(array: Array<Int>)
}