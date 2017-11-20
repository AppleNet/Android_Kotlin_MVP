package com.example.llcgs.android_kotlin.mvvm.generatedbinding

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityGeneratedBindingBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.generatedbinding.adapter.GeneratedAdapter
import com.example.llcgs.android_kotlin.mvvm.generatedbinding.viewmodel.GeneratedBindingViewModel
import kotlinx.android.synthetic.main.activity_generated_binding.*
import kotlinx.android.synthetic.main.view_title.*
import java.util.*

/**
 * com.example.llcgs.android_kotlin.mvvm.generatedbinding.GeneratedBindingActivity
 * @author liulongchao
 * @since 2017/11/17
 */
class GeneratedBindingActivity :BaseActivity<GeneratedBindingViewModel, ActivityGeneratedBindingBinding>() {

    private lateinit var adapter: GeneratedAdapter

    override fun createViewModel()= GeneratedBindingViewModel()

    override fun createViewDataBinding(): ActivityGeneratedBindingBinding {

        // 1.
        val bindingView = ActivityGeneratedBindingBinding.inflate(LayoutInflater.from(this))

        // 2.
        // val bindingViewParams = ActivityGeneratedBindingBinding.inflate(LayoutInflater.from(this), ViewGroup(this), false)

        // 3.
        // val bindigViewBind = ActivityGeneratedBindingBinding.bind(viewRoot)

        // 4.
        // val bindingUtil = DataBindingUtil.inflate(LayoutInflater, layoutId, parent, attachToParent)

        // 5.
        // val bindingUtil = DataBindingUtil.bindTo(viewRoot, layoutId)

        return DataBindingUtil.setContentView(this, R.layout.activity_generated_binding)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "generatedbinding"
    }

    private fun initData(){
        adapter = GeneratedAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        binding.setVariable(BR.generatedBindingViewModel, viewModel)
        viewModel.fetchList(this)
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o is GeneratedBindingViewModel){
            // Views With IDs
            // A public final field will be generated for each View with an ID in the layout.
            // The binding does a single pass on the View hierarchy, extracting the Views with IDs. This mechanism can be faster than calling findViewById for several View
            adapter = binding.recyclerView.adapter as GeneratedAdapter
            adapter.list = o.list
            adapter.notifyDataSetChanged()
        }
    }
}