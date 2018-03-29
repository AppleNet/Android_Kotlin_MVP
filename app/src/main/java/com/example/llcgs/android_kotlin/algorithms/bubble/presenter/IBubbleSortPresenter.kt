package com.example.llcgs.android_kotlin.algorithms.bubble.presenter

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsPresenter
import com.example.llcgs.android_kotlin.base.presenter.SuperPresenter

/**
 * com.example.llcgs.android_kotlin.algorithms.bubble.presenter.IBubbleSortPresenter
 * @author liulongchao
 * @since 2018/3/28
 */
interface IBubbleSortPresenter: BaseAlgorithmsPresenter {
    fun bubbleSort(array: Array<Int>)
}