package com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.adapter.RecyclerViewFragmentAdapter
import com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.viewmodel.ShareViewModel
import com.example.llcgs.android_kotlin.base.fragment.BaseFragment
import com.example.llcgs.android_kotlin.base.presenter.SuperPresenter
import kotlinx.android.synthetic.main.activity_share_fragment_detail.*

/**
 * com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.fragments.MasterFragment
 * @author liulongchao
 * @since 2017/12/1
 */
class DetailFragment : BaseFragment<SuperPresenter>(), Observer<List<String>> {

    private lateinit var adapter: RecyclerViewFragmentAdapter

    override fun getLayoutId() = R.layout.activity_share_fragment_detail

    override fun createPresenter() = ViewModelProviders.of(activity?: FragmentActivity()).get(ShareViewModel::class.java)

    override fun initViews() {
        adapter = RecyclerViewFragmentAdapter()
        detailRecyclerView.adapter = adapter
        detailRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun initData() {
        (mPresenter as ShareViewModel).fetchList().observe(this, this)
    }

    override fun onChanged(t: List<String>?) {
        adapter.setNewData(t)
    }
}