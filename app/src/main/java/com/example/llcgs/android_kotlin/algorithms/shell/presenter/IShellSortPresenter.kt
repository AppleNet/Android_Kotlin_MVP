package com.example.llcgs.android_kotlin.algorithms.shell.presenter

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsPresenter

/**
 * com.example.llcgs.android_kotlin.algorithms.shell.presenter.IShellSortPresenter
 * @author liulongchao
 * @since 2018/3/29
 */
interface IShellSortPresenter: BaseAlgorithmsPresenter {

    fun shellSort(array: Array<Int>)
}