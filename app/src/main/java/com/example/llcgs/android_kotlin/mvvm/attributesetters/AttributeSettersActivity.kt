package com.example.llcgs.android_kotlin.mvvm.attributesetters

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityAttributeSettersBinding
import com.example.llcgs.android_kotlin.mvvm.attributesetters.adapter.MenuAdapter
import com.example.llcgs.android_kotlin.mvvm.attributesetters.viewmodel.AttributeSettersViewModel
import com.example.llcgs.android_kotlin.mvvm.attributesetters.widget.refreshlayout.callback.OnRefreshListener
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.gomejr.myf.core.kotlin.logD
import kotlinx.android.synthetic.main.view_drawerlayout.*
import kotlinx.android.synthetic.main.view_toolbar.*
import java.util.*

/**
 * com.example.llcgs.android_kotlin.mvvm.attributesetters.AttributeSettersActivity
 * @author liulongchao
 * @since 2017/11/22
 */
class AttributeSettersActivity : BaseActivity<AttributeSettersViewModel, ActivityAttributeSettersBinding>() {

    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var menuAdapter: MenuAdapter

    override fun createViewModel() = AttributeSettersViewModel()

    override fun createViewDataBinding():ActivityAttributeSettersBinding = DataBindingUtil.setContentView(this, R.layout.activity_attribute_setters)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    fun initViews() {
        toolBar.title = "attributeSetters" //设置Toolbar标题
        toolBar.setTitleTextColor(Color.parseColor("#ffffff")) //设置标题颜色
        setSupportActionBar(toolBar)
        // DrawerLayout Listener
        mDrawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.open, R.string.close) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }
        }
        mDrawerToggle.syncState()
        drawerLayout.addDrawerListener(mDrawerToggle)
        drawerLayout.right = 200

        // 菜单
        menuAdapter = MenuAdapter()
        menuAdapter.setAdapterListener {
            viewModel.fetchContentList(resources.getStringArray(R.array.databinding_nba), it)
            drawerLayout.closeDrawer(Gravity.START)
        }
        menuRecyclerView.adapter = menuAdapter
        menuRecyclerView.layoutManager = LinearLayoutManager(this)

    }

    fun initData() {
        binding.setVariable(BR.attributeSettersViewModel, viewModel)
        viewModel.fetchMenuList(resources.getStringArray(R.array.databinding_nba))
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o is AttributeSettersViewModel) {
            "arg: $arg".logD()
            if (arg == "list"){
                menuAdapter.list.clear()
                menuAdapter.list = o.list
                menuAdapter.notifyDataSetChanged()
            }
        }
    }

}