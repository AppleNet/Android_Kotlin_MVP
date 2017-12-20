package com.example.llcgs.android_kotlin.material.about

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.about.presenter.IAboutPresenter
import com.example.llcgs.android_kotlin.material.about.presenter.impl.AboutPresenter
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import kotlinx.android.synthetic.main.activity_about.*

/**
 * com.example.llcgs.android_kotlin.material.about.AboutActivity
 * @author liulongchao
 * @since 2017/12/20
 */
class AboutActivity: BaseMaterialActivity<IAboutPresenter>() {

    override fun createPresenter() = AboutPresenter()

    override fun getLayoutId()= R.layout.activity_about

    override fun initViews() {
        setSupportActionBar(toolbar)
        toolbar.title = "豆芽"
    }
}