package com.example.llcgs.android_kotlin.material.profile

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import com.example.llcgs.android_kotlin.material.profile.presenter.IProfilePresenter
import com.example.llcgs.android_kotlin.material.profile.presenter.impl.ProfilePresenter

/**
 * com.example.llcgs.android_kotlin.material.profile.MaterialProfileActivity
 * @author liulongchao
 * @since 2017/12/18
 */
class MaterialProfileActivity : BaseMaterialActivity<IProfilePresenter>() {


    override fun createPresenter()= ProfilePresenter()

    override fun getLayoutId()= R.layout.activity_profile

    override fun initViews() {

    }
}