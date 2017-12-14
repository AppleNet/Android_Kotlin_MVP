package com.example.llcgs.android_kotlin.material.detail

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.detail.presenter.IDetailPresenter
import com.example.llcgs.android_kotlin.material.detail.presenter.impl.DetailPresenter

/**
 * com.example.llcgs.android_kotlin.material.detail.DetailActivity
 * @author liulongchao
 * @since 2017/12/14
 */
class DetailActivity : BaseDetailActivity<IDetailPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.postponeEnterTransition(this)
    }

    override fun createPresenter()= DetailPresenter()

    override fun getLayoutId()= R.layout.activity_detail

    override fun initViews() {

    }
}