package com.example.llcgs.android_kotlin.mvvm.login

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.example.llcgs.android_kotlin.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.login.model.User
import com.example.llcgs.android_kotlin.mvvm.login.view.MvvmView
import com.example.llcgs.android_kotlin.mvvm.login.viewmodel.HomeViewModel
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.view_title.*

@RouterRule("mvvm")
class MvvmActivity : BaseActivity<HomeViewModel, ViewDataBinding>(), MvvmView {

    override fun createViewModel()= HomeViewModel(this)

    override fun createViewDataBinding(): ViewDataBinding = DataBindingUtil.setContentView<ViewDataBinding>(this@MvvmActivity, R.layout.activity_mvvm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.viewModel, viewModel)
        initViews()
    }

    private fun initViews(){
        pluginTitleTV.text = "Mvvm"
    }

    override fun onLoginSuccess(user: User) {

    }
}
