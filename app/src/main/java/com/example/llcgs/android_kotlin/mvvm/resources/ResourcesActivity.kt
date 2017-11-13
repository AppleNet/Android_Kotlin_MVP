package com.example.llcgs.android_kotlin.mvvm.resources

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityResourcesBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.resources.viewmodel.ResourcesViewModel
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.mvvm.resources.ResourcesActivity
 * @author liulongchao
 * @since 2017/11/13
 */


class ResourcesActivity:BaseActivity<ResourcesViewModel, ActivityResourcesBinding>() {

    override fun createViewModel()= ResourcesViewModel()

    override fun createViewDataBinding(): ActivityResourcesBinding= DataBindingUtil.setContentView(this, R.layout.activity_resources)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "resources"
    }

    private fun initData(){
        binding.setVariable(BR.resourcesViewModel, viewModel)
    }
}