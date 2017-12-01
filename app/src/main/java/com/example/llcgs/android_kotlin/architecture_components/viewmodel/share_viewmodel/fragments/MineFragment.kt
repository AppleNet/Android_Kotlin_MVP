package com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.adapter.RecyclerViewFragmentAdapter
import com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.viewmodel.ShareViewModel
import com.example.llcgs.android_kotlin.base.fragment.BaseFragment
import kotlinx.android.synthetic.main.activity_share_fragment_mine.*
import com.example.llcgs.android_kotlin.base.presenter.SuperPresenter

/**
 * com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.fragments.MasterFragment
 * @author liulongchao
 * @since 2017/12/1
 */
class MineFragment : BaseFragment<SuperPresenter>(), Observer<List<String>> {

    private lateinit var adapter: RecyclerViewFragmentAdapter

    override fun getLayoutId() = R.layout.activity_share_fragment_mine

    override fun createPresenter() = ViewModelProviders.of(activity).get(ShareViewModel::class.java)

    override fun initViews() {
        adapter = RecyclerViewFragmentAdapter()
        mineRecyclerView.adapter = adapter
        mineRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun initData() {
        (mPresenter as ShareViewModel).fetchList().observe(this, this)
    }

    override fun onChanged(t: List<String>?) {
        adapter.setNewData(t)
    }
}