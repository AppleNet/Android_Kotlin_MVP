package com.example.llcgs.android_kotlin.algorithms.insert.presenter

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsPresenter

/**
 * com.example.llcgs.android_kotlin.algorithms.insert.presenter.IInsertSortPresenter
 * @author liulongchao
 * @since 2018/3/29
 */
interface IInsertSortPresenter: BaseAlgorithmsPresenter {

    fun insertSort(array: Array<Int>)
    fun secondInsertSort(array: Array<Int>)
}