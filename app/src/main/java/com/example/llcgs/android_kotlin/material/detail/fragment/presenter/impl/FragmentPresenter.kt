package com.example.llcgs.android_kotlin.material.detail.fragment.presenter.impl

import com.example.llcgs.android_kotlin.base.rx.RxUtils
import com.example.llcgs.android_kotlin.material.detail.fragment.model.FragmentModel
import com.example.llcgs.android_kotlin.material.detail.fragment.presenter.IFragmentPresenter
import com.example.llcgs.android_kotlin.material.detail.fragment.view.FragmentView

/**
 * com.example.llcgs.android_kotlin.material.detail.fragment.presenter.impl.FragmentPresenter
 * @author liulongchao
 * @since 2017/12/22
 */
class FragmentPresenter(private val view: FragmentView) : IFragmentPresenter {

    private val model = FragmentModel()

    override fun loadLikers() {
        model.loadLikers()
                .compose(RxUtils.defaultTransformer(view))
                .subscribe{
                    view.onGetLikers(it)
                }
    }

    override fun loadRebroadcast() {
        model.loadRebroadcast()
                .compose(RxUtils.defaultTransformer(view))
                .subscribe {
                    view.onGetRebroadcast(it)
                }
    }


}