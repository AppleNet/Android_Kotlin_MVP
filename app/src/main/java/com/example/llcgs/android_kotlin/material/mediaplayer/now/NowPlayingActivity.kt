package com.example.llcgs.android_kotlin.material.mediaplayer.now

import com.example.llcgs.android_kotlin.material.mediaplayer.base.BaseMediaActivity
import com.example.llcgs.android_kotlin.material.mediaplayer.now.presenter.INowPlayingPresenter
import com.example.llcgs.android_kotlin.material.mediaplayer.now.presenter.impl.NowPlayingPresenter

/**
 * com.example.llcgs.android_kotlin.material.mediaplayer.now.NowPlayingActivity
 * @author liulongchao
 * @since 2018/1/23
 */
class NowPlayingActivity: BaseMediaActivity<INowPlayingPresenter>() {
    
    override fun createPresenter(): INowPlayingPresenter= NowPlayingPresenter()

    override fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initViews() {

    }
}