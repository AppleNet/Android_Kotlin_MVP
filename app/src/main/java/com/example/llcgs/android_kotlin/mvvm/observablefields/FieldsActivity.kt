package com.example.llcgs.android_kotlin.mvvm.observablefields

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Gravity
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityFieldsBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.observablefields.viewmodel.FieldsViewModel
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.mvvm.observablefields.FieldsActivity
 * @author liulongchao
 * @since 2017/11/15
 */

/**
 *  Databinding 在xml中不适用于 layout_的所有属性 所有引用drawable的属性
 * */
class FieldsActivity: BaseActivity<FieldsViewModel, ActivityFieldsBinding>() {

    override fun createViewModel()= FieldsViewModel()

    override fun createViewDataBinding(): ActivityFieldsBinding =DataBindingUtil.setContentView(this, R.layout.activity_fields)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        initData()
    }

    private fun initData() {
        //
        binding.setVariable(BR.fieldViewModel, viewModel)
    }

    private fun initViews(){
        pluginTitleTV.text = "observableFields"
    }
}