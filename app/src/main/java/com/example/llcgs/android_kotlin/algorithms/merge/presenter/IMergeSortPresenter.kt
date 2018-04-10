package com.example.llcgs.android_kotlin.algorithms.merge.presenter

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsPresenter

/**
 * com.example.llcgs.android_kotlin.algorithms.merge.presenter.IMergeSortPresenter
 * @author liulongchao
 * @since 2018/4/2
 */
interface IMergeSortPresenter: BaseAlgorithmsPresenter {

    fun mergeSort(array: Array<Int>)
}