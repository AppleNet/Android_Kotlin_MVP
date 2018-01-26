package com.example.llcgs.android_kotlin.material.mediaplayer.fullscreen

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.mediaplayer.base.BaseMediaActivity
import com.example.llcgs.android_kotlin.material.mediaplayer.fullscreen.presenter.IFullScreenPlayerPresenter
import com.example.llcgs.android_kotlin.material.mediaplayer.fullscreen.presenter.impl.FullScreenPlayerPresenter

/**
 * com.example.llcgs.android_kotlin.material.mediaplayer.fullscreen.FullScreenPlayerActivity
 * @author liulongchao
 * @since 2018/1/24
 */
class FullScreenPlayerActivity: BaseMediaActivity<IFullScreenPlayerPresenter>() {

    override fun createPresenter(): IFullScreenPlayerPresenter = FullScreenPlayerPresenter()

    override fun getLayoutId() = R.layout.activity_full_player

    override fun initViews() {

    }
}