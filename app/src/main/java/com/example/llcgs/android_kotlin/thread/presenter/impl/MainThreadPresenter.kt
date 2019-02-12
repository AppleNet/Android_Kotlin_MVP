package com.example.llcgs.android_kotlin.thread.presenter.impl;

import com.example.llcgs.android_kotlin.base.presenter.BasePresenter
import com.example.llcgs.android_kotlin.thread.model.MainThreadModel
import com.example.llcgs.android_kotlin.thread.presenter.IMainThreadPresenter
import com.example.llcgs.android_kotlin.thread.view.MainThreadView

/**
 * com.example.llcgs.android_kotlin.thread.presenter.impl.MainThreadPresenter
 * @author liulongchao
 * @since 2019/1/30
 */
class MainThreadPresenter: IMainThreadPresenter, BasePresenter<MainThreadView>() {

    val model = MainThreadModel()

    override fun testWait() {
        model.testWait()
    }

    override fun testNotify() {
        model.testNotify()
    }

    override fun initThread() {
        model.initThread()
    }

    override fun testJoin() {
        model.testJoin()
    }

    override fun testJoin1() {
        model.testJoin1()
    }
}
