package com.example.llcgs.android_kotlin.algorithms.heap.presenter

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsPresenter

/**
 * com.example.llcgs.android_kotlin.algorithms.heap.presenter.IHeapSortPresenter
 * @author liulongchao
 * @since 2018/3/30
 */
interface IHeapSortPresenter: BaseAlgorithmsPresenter {
    fun heapSort(array: Array<Int>)
}