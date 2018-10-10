package com.example.llcgs.android_kotlin.mvvm.observablecollections

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ObservableArrayMap
import android.os.Bundle
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityObservableCollectionsBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.observablecollections.view.ObservableCollectionsView
import com.example.llcgs.android_kotlin.mvvm.observablecollections.viewmodel.ObservableCollectionsViewModel
import kotlinx.android.synthetic.main.view_title.*
import kotlin.collections.ArrayList

/**
 * com.example.llcgs.android_kotlin.mvvm.observablecollections.ObservableCollectionsActivity
 * @author liulongchao
 * @since 2017/11/16
 */
class ObservableCollectionsActivity:BaseActivity<ObservableCollectionsViewModel, ActivityObservableCollectionsBinding>(), ObservableCollectionsView {

    override fun createViewModel()= ObservableCollectionsViewModel(this)

    override fun createViewDataBinding(): ActivityObservableCollectionsBinding= DataBindingUtil.setContentView(this, R.layout.activity_observable_collections)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "observableCollections"
    }

    private fun initData(){
        /**
         *  Observable Collections
         *    1. ObservableArrayMap
         *    2. ObservableArrayList
         * */
        binding.arraylist = ObservableArrayList<String>().apply {
            addAll(arrayListOf("Kobe", "James", "McGrady", "Wade"))
        }
        binding.arraymap = ObservableArrayMap<String, Any>().apply {
            putAll(mapOf("0" to "Wade", "1" to "McGrady", "2" to "James", "3" to "Kobe"))
        }
        binding.index = 0
        binding.key = "0"
        binding.setVariable(BR.observableCollectionsViewModel, viewModel)
    }

    override fun onGetIndex(index: Int) {
        binding.index = index
    }

    override fun onGetKey(key: String) {
        binding.key = key
    }

    override fun onGetList(list: ArrayList<String>) {
        binding.arraylist?.addAll(list)
    }
}