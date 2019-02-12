package com.example.llcgs.android_kotlin.thread

import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.thread.presenter.impl.MainThreadPresenter
import com.example.llcgs.android_kotlin.thread.view.MainThreadView
import com.lzh.nonview.router.anno.RouterRule


@RouterRule("Thread")
class MainThreadActivity : BaseActivity<MainThreadView, MainThreadPresenter>() {


    override fun createPresenter()= MainThreadPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_thread)
        // initThread();
        // testWait();
        // testNotify();
        // testJoin();
        testJoin1()
    }

    private fun initThread() {
        mPresenter.initThread()
    }

    private fun testWait() {
        mPresenter.testWait()
    }

    private fun testNotify() {
        mPresenter.testNotify()
    }

    private fun testJoin() {
        mPresenter.testJoin()
    }

    private fun testJoin1() {
        mPresenter.testJoin1()
    }
}
