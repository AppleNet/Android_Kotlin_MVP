package com.example.llcgs.android_kotlin.mvvm.layoutdetails

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityDetailsBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.layoutdetails.model.User
import com.example.llcgs.android_kotlin.mvvm.layoutdetails.viewmodel.LayoutDetailsViewModel
import kotlinx.android.synthetic.main.view_title.*
import java.util.*

/**
 * com.example.llcgs.android_kotlin.mvvm.layoutdetails.LayoutDetailsActivity
 * @author liulongchao
 * @since 2017/11/4
 */


class LayoutDetailsActivity:BaseActivity<LayoutDetailsViewModel, ActivityDetailsBinding>() {


    override fun createViewModel()= LayoutDetailsViewModel()

    override fun createViewDataBinding(): ActivityDetailsBinding= DataBindingUtil.setContentView(this, R.layout.activity_details)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pluginTitleTV.text = "LayoutDetails"
        initData()
    }

    private fun initData() {
        viewModel.addObserver(this)
        val user = User().apply {
            id = "1"
            name = "Kobe"
            gender = "man"
            height = 196
            hobby = "basketball"
            hasAbdominal = true
        }
        viewModel.user = user
        binding.setVariable(BR.layoutDetailsViewModel, viewModel)
    }

    override fun update(o: Observable?, arg: Any?) {
        super.update(o, arg)
    }

}