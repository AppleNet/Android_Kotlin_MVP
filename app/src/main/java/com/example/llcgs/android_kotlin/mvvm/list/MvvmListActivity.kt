package com.example.llcgs.android_kotlin.mvvm.list

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityMvvmListBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.list.adapter.StudentAdapter
import com.example.llcgs.android_kotlin.mvvm.list.viewmodel.MvvmListViewModel
import kotlinx.android.synthetic.main.activity_mvvm_list.*
import kotlinx.android.synthetic.main.view_title.*
import java.util.*

/**
 * com.example.llcgs.android_kotlin.mvvm.list.MvvmListActivity
 * @author liulongchao
 * @since 2017/10/26
 */


class MvvmListActivity: BaseActivity<MvvmListViewModel, ViewDataBinding>() {

    private lateinit var adapter: StudentAdapter

    override fun createViewModel()= MvvmListViewModel()

    override fun createViewDataBinding(): ViewDataBinding= DataBindingUtil.setContentView<ActivityMvvmListBinding>(this, R.layout.activity_mvvm_list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "List"
        adapter = StudentAdapter()
        list_item.adapter = adapter
        list_item.layoutManager = LinearLayoutManager(this)
        viewModel.addObserver(this)
        binding.setVariable(BR.mvvmListViewModel, viewModel)

    }

    private fun initData(){
        viewModel.fetchStudentList()
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o is MvvmListViewModel){
            adapter = ((binding as ActivityMvvmListBinding).listItem.adapter) as StudentAdapter
            adapter.list = o.peopleList
            adapter.notifyDataSetChanged()
        }
    }

}