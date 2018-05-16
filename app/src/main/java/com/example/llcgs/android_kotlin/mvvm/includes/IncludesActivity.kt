package com.example.llcgs.android_kotlin.mvvm.includes

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityIncludesBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.includes.model.Footer
import com.example.llcgs.android_kotlin.mvvm.includes.model.Header
import com.example.llcgs.android_kotlin.mvvm.includes.viewmodel.IncludesViewModel
import com.example.llcgs.android_kotlin.utils.log.logD
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.mvvm.includes.IncludesActivity
 * @author liulongchao
 * @since 2017/11/10
 */


class IncludesActivity:BaseActivity<IncludesViewModel, ActivityIncludesBinding>() {


    override fun createViewModel()= IncludesViewModel()

    override fun createViewDataBinding(): ActivityIncludesBinding = DataBindingUtil.setContentView(this, R.layout.activity_includes)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "Includes"
    }

    private fun initData(){
        val header = Header()
        header.headerTitle = "I am header"
        header.headerContent = "I am headerContent"

        val footer = Footer()
        footer.footerTitle = "I am footer"
        footer.footerContent = "I am footer content"

        viewModel.header = header
        viewModel.footer = footer
        val variable = binding.setVariable(BR.includeViewModel, viewModel)
        variable.logD()
    }
}