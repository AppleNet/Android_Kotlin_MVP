package com.example.llcgs.android_kotlin.algorithms.bubble.view

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsView
import com.example.llcgs.android_kotlin.base.view.BaseView

/**
 * com.example.llcgs.android_kotlin.algorithms.bubble.view.BubbleSortView
 * @author liulongchao
 * @since 2018/3/28
 */
interface BubbleSortView: BaseAlgorithmsView {
    fun onGetBubbleSortArray(array: Array<Int>)
}