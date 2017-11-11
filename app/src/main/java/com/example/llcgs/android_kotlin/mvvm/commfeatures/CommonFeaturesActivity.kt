package com.example.llcgs.android_kotlin.mvvm.commfeatures

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityCommonFeaturesBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.commfeatures.filters.NoEmptyFilters
import com.example.llcgs.android_kotlin.mvvm.commfeatures.viewmodel.CommonFeaturesViewModel
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.mvvm.commfeatures.CommonFeaturesActivity
 * @author liulongchao
 * @since 2017/11/11
 */


class CommonFeaturesActivity : BaseActivity<CommonFeaturesViewModel, ActivityCommonFeaturesBinding>(){

    override fun createViewModel()= CommonFeaturesViewModel()

    override fun createViewDataBinding(): ActivityCommonFeaturesBinding= DataBindingUtil.setContentView(this, R.layout.activity_common_features)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "CommonFeatures"
        binding.numberOne.filters = arrayOf(NoEmptyFilters())
        binding.numberTwo.filters = arrayOf(NoEmptyFilters())
    }

    private fun initData(){
        binding.setVariable(BR.commonFeaturesViewModel, viewModel)
    }

}