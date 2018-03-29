package com.example.llcgs.android_kotlin.algorithms.insert.view

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsView

/**
 * com.example.llcgs.android_kotlin.algorithms.insert.view.InsertSortView
 * @author liulongchao
 * @since 2018/3/29
 */
interface InsertSortView: BaseAlgorithmsView {
    fun onGetSortResult(array: Array<Int>)
    fun onGetSecondSortResult(array: Array<Int>)
}