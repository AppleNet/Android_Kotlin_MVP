package com.example.llcgs.android_kotlin.material.douya.about

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.douya.about.presenter.IAboutPresenter
import com.example.llcgs.android_kotlin.material.douya.about.presenter.impl.AboutPresenter
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import kotlinx.android.synthetic.main.activity_about.*

/**
 * com.example.llcgs.android_kotlin.material.about.MaterialAboutActivity
 * @author liulongchao
 * @since 2017/12/20
 */
class MaterialAboutActivity : BaseMaterialActivity<com.example.llcgs.android_kotlin.material.douya.about.presenter.IAboutPresenter>() {

    override fun createPresenter() = com.example.llcgs.android_kotlin.material.douya.about.presenter.impl.AboutPresenter()

    override fun getLayoutId()= R.layout.activity_about

    override fun initViews() {
        setSupportActionBar(toolbar)
        toolbar.title = "豆芽"
    }
}