package com.example.llcgs.android_kotlin.thread.presenter;

import com.example.llcgs.android_kotlin.base.presenter.SuperPresenter

/**
 * com.example.llcgs.android_kotlin.thread.presenter.IMainThreadPresenter
 * @author liulongchao
 * @since 2019/1/30
 */
interface IMainThreadPresenter: SuperPresenter {

    fun initThread()
    fun testWait()
    fun testNotify()
    fun testJoin()
}