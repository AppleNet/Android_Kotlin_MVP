package com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.base.BaseOwnerActivity
import com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.adapter.ShareAdapter
import com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.fragments.DetailFragment
import com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.fragments.MasterFragment
import com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.fragments.MineFragment
import com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.viewmodel.ShareViewModel
import com.example.llcgs.android_kotlin.base.fragment.BaseFragment
import com.example.llcgs.android_kotlin.base.presenter.SuperPresenter
import com.example.llcgs.android_kotlin.utils.log.logD
import kotlinx.android.synthetic.main.activity_share_view_model.*
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.ShareViewModelActivity
 * @author liulongchao
 * @since 2017/11/30
 */
class ShareViewModelActivity:BaseOwnerActivity<ShareViewModel>(), ViewPager.OnPageChangeListener {

    private lateinit var adapter: ShareAdapter<BaseFragment<SuperPresenter>>
    private lateinit var fragments: ArrayList<BaseFragment<SuperPresenter>>
    private val masterFragment = MasterFragment()
    private val detailFragment = DetailFragment()
    private val mineFragment = MineFragment()

    override fun createPresenter(): ShareViewModel= ViewModelProviders.of(this).get(ShareViewModel::class.java)

    override fun getLayoutId(): Int= R.layout.activity_share_view_model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "ShareViewModel"
        fragments = arrayListOf(masterFragment, detailFragment, mineFragment)
        adapter = ShareAdapter(supportFragmentManager, fragments)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 0
        viewPager.addOnPageChangeListener(this)

        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)?.text = "Master"
        tabLayout.getTabAt(1)?.text = "Detail"
        tabLayout.getTabAt(2)?.text = "Mine"

    }

    private fun initData(){
        //
        mPresenter.setData(resources.getStringArray(R.array.arch_menu).toList())
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        position.logD()
        when(position){
            0 ->{
                // master
                mPresenter.setData(resources.getStringArray(R.array.arch_menu).toList())
            }
            1 ->{
                // detail
                mPresenter.setData(resources.getStringArray(R.array.architecture).toList())
            }
            2 ->{
                // mine
                mPresenter.setData(resources.getStringArray(R.array.databinding_menu).toList())
            }
        }
    }
}