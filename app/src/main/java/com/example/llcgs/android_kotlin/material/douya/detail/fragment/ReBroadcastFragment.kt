package com.example.llcgs.android_kotlin.material.douya.detail.fragment

import android.support.v7.widget.LinearLayoutManager
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialFragment
import com.example.llcgs.android_kotlin.material.douya.detail.fragment.adapter.LikeAdapter
import com.example.llcgs.android_kotlin.material.douya.detail.fragment.bean.LikeBroadcast
import com.example.llcgs.android_kotlin.material.douya.detail.fragment.presenter.IFragmentPresenter
import com.example.llcgs.android_kotlin.material.douya.detail.fragment.presenter.impl.FragmentPresenter
import com.example.llcgs.android_kotlin.material.douya.detail.fragment.view.FragmentView
import kotlinx.android.synthetic.main.fragment_like.*

/**
 * com.example.llcgs.android_kotlin.material.detail.fragment.LikeFragment
 * @author liulongchao
 * @since 2017/12/18
 */
class ReBroadcastFragment: BaseMaterialFragment<com.example.llcgs.android_kotlin.material.douya.detail.fragment.presenter.IFragmentPresenter>(), com.example.llcgs.android_kotlin.material.douya.detail.fragment.view.FragmentView {

    private lateinit var adapter: com.example.llcgs.android_kotlin.material.douya.detail.fragment.adapter.LikeAdapter

    override fun getLayoutId()= R.layout.fragment_like

    override fun createPresenter()= com.example.llcgs.android_kotlin.material.douya.detail.fragment.presenter.impl.FragmentPresenter(this)

    override fun initViews() {
        adapter = com.example.llcgs.android_kotlin.material.douya.detail.fragment.adapter.LikeAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun initData() {
        mPresenter.loadRebroadcast()
    }

    override fun onGetLikers(list: List<com.example.llcgs.android_kotlin.material.douya.detail.fragment.bean.LikeBroadcast>) {
    }

    override fun onGetRebroadcast(list: List<com.example.llcgs.android_kotlin.material.douya.detail.fragment.bean.LikeBroadcast>) {
        adapter.setNewData(list)
        listener?.let {
            it(list.size)
        }
    }

    private var listener:((size: Int)->Unit)? = null

    fun setRebroadcastListener(listener:((size: Int)->Unit)){
        this.listener = listener
    }
}