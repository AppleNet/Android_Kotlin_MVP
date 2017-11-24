package com.example.llcgs.android_kotlin.mvvm.converters

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityConvertersBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.converters.adapter.ConvertersAdapter
import com.example.llcgs.android_kotlin.mvvm.converters.fragment.CustomConvertersFragment
import com.example.llcgs.android_kotlin.mvvm.converters.fragment.MyConvertersFragment
import com.example.llcgs.android_kotlin.mvvm.converters.fragment.ObjectConvertersFragment
import com.example.llcgs.android_kotlin.mvvm.converters.viewmodel.ConvertersViewModel
import kotlinx.android.synthetic.main.activity_converters.*

/**
 * com.example.llcgs.android_kotlin.mvvm.converters.ConvertersActivity
 * @author liulongchao
 * @since 2017/11/24
 */
class ConvertersActivity:BaseActivity<ConvertersViewModel, ActivityConvertersBinding>(), AppBarLayout.OnOffsetChangedListener {

    private lateinit var adapter: ConvertersAdapter
    private var fragments = ArrayList<Fragment>()

    override fun createViewModel()= ConvertersViewModel()

    override fun createViewDataBinding(): ActivityConvertersBinding = DataBindingUtil.setContentView(this, R.layout.activity_converters)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews(){
        toolBar.title = "Converters" //设置Toolbar标题
        toolBar.setTitleTextColor(Color.parseColor("#ffffff")) //设置标题颜色
        setSupportActionBar(toolBar)

        appBarLayout.addOnOffsetChangedListener(this)

        fragments.add(ObjectConvertersFragment())
        fragments.add(CustomConvertersFragment())
        fragments.add(MyConvertersFragment())

        adapter = ConvertersAdapter(supportFragmentManager, fragments)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 0

        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)?.text = "Object Conversions"
        tabLayout.getTabAt(1)?.text = "Custom Conversions"
        tabLayout.getTabAt(2)?.text = "My Conversions"
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (verticalOffset == 0){
            // 展开状态
            toolBar.title = "Converters"
            collapsingToolbarLayout.title = "Converters"
        }else if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange){
            // 折叠状态
            toolBar.title = ""
            collapsingToolbarLayout.title = ""
        }else{
            // 中间状态
        }
    }
}
