package com.example.llcgs.android_kotlin.mvvm

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.example.llcgs.android_kotlin.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.viewmodel.HomeViewModel
import com.lzh.nonview.router.anno.RouterRule

@RouterRule("mvvm")
class MvvmActivity : BaseActivity<HomeViewModel, ViewDataBinding>() {

    override fun createViewModel()= HomeViewModel()

    override fun createViewDataBinding(): ViewDataBinding = DataBindingUtil.setContentView<ViewDataBinding>(this@MvvmActivity, R.layout.activity_mvvm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.viewModel, viewModel)
    }
}
