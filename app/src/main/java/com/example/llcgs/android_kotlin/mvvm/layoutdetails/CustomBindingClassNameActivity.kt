package com.example.llcgs.android_kotlin.mvvm.layoutdetails

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.layoutdetails.model.CustomUser
import com.example.llcgs.android_kotlin.mvvm.layoutdetails.viewmodel.CustomBindingClassNameViewModel
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.mvvm.layoutdetails.CustomBindingClassNameActivity
 * @author liulongchao
 * @since 2017/11/6
 */


class CustomBindingClassNameActivity:BaseActivity<CustomBindingClassNameViewModel, ActivityCustomBindingClassName>() {

    override fun createViewModel()= CustomBindingClassNameViewModel()

    override fun createViewDataBinding(): ActivityCustomBindingClassName= DataBindingUtil.setContentView(this, R.layout.activity_custom_binding_class_name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "CustomBindingClassName"
    }

    private fun initData(){
        val customUser = CustomUser().apply {
            name = "customUser"
        }
        viewModel.customUser = customUser
        binding.setVariable(BR.customBindingClassNameViewModel, viewModel)
    }
}