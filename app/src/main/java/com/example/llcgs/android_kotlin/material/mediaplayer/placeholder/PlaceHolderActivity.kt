package com.example.llcgs.android_kotlin.material.mediaplayer.placeholder

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.mediaplayer.base.BaseMediaActivity
import com.example.llcgs.android_kotlin.material.mediaplayer.placeholder.presenter.IPlaceHolderPresenter
import com.example.llcgs.android_kotlin.material.mediaplayer.placeholder.presenter.impl.PlaceHolderPresenter

/**
 * com.example.llcgs.android_kotlin.material.mediaplayer.place.PlaceHolderActivity
 * @author liulongchao
 * @since 2018/1/19
 */
class PlaceHolderActivity : BaseMediaActivity<IPlaceHolderPresenter>(){

    override fun createPresenter(): IPlaceHolderPresenter= PlaceHolderPresenter()

    override fun getLayoutId() = R.layout.activity_place_holder

    override fun initViews() {
        initializeToolbar()
    }
}