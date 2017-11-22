package com.example.llcgs.android_kotlin.mvvm.advancedbinding

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityAdvancedBindingBinding
import com.example.llcgs.android_kotlin.mvvm.advancedbinding.adapter.AdvancedBindingAdapter
import com.example.llcgs.android_kotlin.mvvm.advancedbinding.viewmodel.AdvancedBindingViewModel
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_advanced_binding.*
import kotlinx.android.synthetic.main.view_title.*
import java.util.*

/**
 * com.example.llcgs.android_kotlin.mvvm.advancedbinding.AdvancedBindingActivity
 * @author liulongchao
 * @since 2017/11/20
 */
class AdvancedBindingActivity:BaseActivity<AdvancedBindingViewModel, ActivityAdvancedBindingBinding>() {

    private lateinit var adapter:AdvancedBindingAdapter

    override fun createViewModel()= AdvancedBindingViewModel()

    override fun createViewDataBinding(): ActivityAdvancedBindingBinding= DataBindingUtil.setContentView(this, R.layout.activity_advanced_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "advancedBinding"
        adapter = AdvancedBindingAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initData(){
        binding.setVariable(BR.advancedBindingViewModel, viewModel)
        viewModel.fetchList(resources.getStringArray(R.array.databinding_nba))
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o is AdvancedBindingViewModel){
            adapter = binding.recyclerView.adapter as AdvancedBindingAdapter
            adapter.list = o.list
            adapter.notifyDataSetChanged()
        }
    }
}