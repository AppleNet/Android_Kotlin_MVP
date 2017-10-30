package com.example.llcgs.android_kotlin.mvvm.list

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.llcgs.android_kotlin.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.list.viewmodel.MvvmListViewModel
import kotlinx.android.synthetic.main.activity_mvvm_list.*
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.mvvm.list.MvvmListActivity
 * @author liulongchao
 * @since 2017/10/26
 */


class MvvmListActivity: BaseActivity<MvvmListViewModel, ViewDataBinding>() {


    override fun createViewModel()= MvvmListViewModel()

    override fun createViewDataBinding(): ViewDataBinding= DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_mvvm_list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "List"
        list_item.layoutManager = LinearLayoutManager(this)

        binding.setVariable(BR.viewModel, viewModel)
    }

    private fun initData(){
        viewModel.fetchStudentList()
    }


}