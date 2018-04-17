package com.example.llcgs.android_kotlin.algorithms.cardinality.presenter

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsPresenter

/**
 * com.example.llcgs.android_kotlin.algorithms.cardinality.presenter.ICardinalitySortPresenter
 * @author liulongchao
 * @since 2018/4/10
 */
interface ICardinalitySortPresenter: BaseAlgorithmsPresenter {

    fun cardSort(array: Array<Int>)
}