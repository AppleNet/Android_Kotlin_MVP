package com.example.llcgs.android_kotlin.mvvm.menu

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityMenuBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.menu.adapter.MenuAdapter
import com.example.llcgs.android_kotlin.mvvm.menu.viewmodel.MenuViewModel
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.view_title.*
import java.util.*

/**
 * com.example.llcgs.android_kotlin.mvvm.menu.MenuActivity
 * @author liulongchao
 * @since 2017/11/11
 */


class MenuActivity:BaseActivity<MenuViewModel, ActivityMenuBinding>() {

    private lateinit var adapter: MenuAdapter

    override fun createViewModel()= MenuViewModel()

    override fun createViewDataBinding(): ActivityMenuBinding = DataBindingUtil.setContentView(this, R.layout.activity_menu)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "Menu"
        adapter = MenuAdapter()
        menuRecyclerView.adapter = adapter
        menuRecyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.addObserver(this)
        binding.setVariable(BR.menuViewModel, viewModel)
    }

    private fun initData(){
        viewModel.fetchMenuList(this)
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o is MenuViewModel){
            adapter = binding.menuRecyclerView.adapter as MenuAdapter
            adapter.list = o.list
            adapter.notifyDataSetChanged()
        }
    }
}