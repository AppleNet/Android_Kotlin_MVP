package com.example.llcgs.android_kotlin.mvvm.attributesetters

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityAttributeSettersBinding
import com.example.llcgs.android_kotlin.mvvm.attributesetters.viewmodel.AttributeSettersViewModel
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import kotlinx.android.synthetic.main.view_drawerlayout.*
import kotlinx.android.synthetic.main.view_toolbar.*

/**
 * com.example.llcgs.android_kotlin.mvvm.attributesetters.AttributeSettersActivity
 * @author liulongchao
 * @since 2017/11/22
 */
class AttributeSettersActivity:BaseActivity<AttributeSettersViewModel, ActivityAttributeSettersBinding>() {

    private lateinit var mDrawerToggle: ActionBarDrawerToggle

    override fun createViewModel()= AttributeSettersViewModel()

    override fun createViewDataBinding(): ActivityAttributeSettersBinding= DataBindingUtil.setContentView(this, R.layout.activity_attribute_setters)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        initData()
    }

    fun initViews(){
        toolBar.title = "attributeSetters" //设置Toolbar标题
        toolBar.setTitleTextColor(Color.parseColor("#ffffff")) //设置标题颜色
        setSupportActionBar(toolBar)
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

    }

    fun initData(){

    }
}