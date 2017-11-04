package com.example.llcgs.android_kotlin.mvvm.layoutdetails

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityDetailsBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.layoutdetails.model.User
import com.example.llcgs.android_kotlin.mvvm.layoutdetails.viewmodel.LayoutDetailsModel
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.mvvm.layoutdetails.LayoutDetailsActivity
 * @author liulongchao
 * @since 2017/11/4
 */


class LayoutDetailsActivity:BaseActivity<LayoutDetailsModel, ActivityDetailsBinding>() {


    override fun createViewModel()= LayoutDetailsModel()

    override fun createViewDataBinding(): ActivityDetailsBinding= DataBindingUtil.setContentView(this, R.layout.activity_details)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pluginTitleTV.text = "LayoutDetails"
        initData()
    }

    private fun initData() {
        val user = User().apply {
            id = "1"
            name = "Kobe"
            gender = "man"
            height = 196
            hobby = "basketball"
            hasAbdominal = true
        }
        viewModel.user = user
        binding.viewmodel = viewModel
    }


}