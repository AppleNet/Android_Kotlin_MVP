package com.example.llcgs.android_kotlin.algorithms.cardinality.view

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsView

/**
 * com.example.llcgs.android_kotlin.algorithms.cardinality.view.CardinalitySortView
 * @author liulongchao
 * @since 2018/4/10
 */
interface CardinalitySortView: BaseAlgorithmsView {

    fun onGetCardSort(array: Array<Int>)
}