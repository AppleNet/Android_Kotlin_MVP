package com.example.llcgs.android_kotlin.material.detail.fragment

import android.support.v7.widget.LinearLayoutManager
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialFragment
import com.example.llcgs.android_kotlin.material.detail.fragment.adapter.LikeAdapter
import com.example.llcgs.android_kotlin.material.detail.fragment.bean.LikeBroadcast
import com.example.llcgs.android_kotlin.material.detail.fragment.presenter.IFragmentPresenter
import com.example.llcgs.android_kotlin.material.detail.fragment.presenter.impl.FragmentPresenter
import com.example.llcgs.android_kotlin.material.detail.fragment.view.FragmentView
import kotlinx.android.synthetic.main.fragment_like.*

/**
 * com.example.llcgs.android_kotlin.material.detail.fragment.LikeFragment
 * @author liulongchao
 * @since 2017/12/18
 */
class LikeFragment: BaseMaterialFragment<IFragmentPresenter>(), FragmentView {

    private lateinit var adapter: LikeAdapter

    override fun getLayoutId()= R.layout.fragment_like

    override fun createPresenter()= FragmentPresenter(this)

    override fun initViews() {
        adapter = LikeAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun initData() {
        mPresenter.loadLikers()
    }

    override fun onGetLikers(list: List<LikeBroadcast>) {
        adapter.setNewData(list)
        listener?.let {
            it(list.size)
        }
    }

    override fun onGetRebroadcast(list: List<LikeBroadcast>) {
    }

    private var listener:((size: Int)->Unit)? = null

    fun setListener(listener:((size: Int)->Unit)){
        this.listener = listener
    }

}