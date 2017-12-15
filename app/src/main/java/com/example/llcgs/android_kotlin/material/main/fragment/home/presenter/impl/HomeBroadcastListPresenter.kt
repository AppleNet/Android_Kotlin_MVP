package com.example.llcgs.android_kotlin.material.main.fragment.home.presenter.impl

import android.app.Activity
import android.content.Context
import android.view.View
import com.example.llcgs.android_kotlin.base.rx.RxUtils
import com.example.llcgs.android_kotlin.material.main.fragment.home.model.HomeBroadcastListModel
import com.example.llcgs.android_kotlin.material.main.fragment.home.presenter.IHomeBroadcastListPresenter
import com.example.llcgs.android_kotlin.material.main.fragment.home.view.HomeBroadcastListView

/**
 * com.example.llcgs.android_kotlin.material.main.fragment.home.presenter.impl.HomeBroadcastListPresenter
 * @author liulongchao
 * @since 2017/12/13
 */
class HomeBroadcastListPresenter(private val view: HomeBroadcastListView) : IHomeBroadcastListPresenter {

    private val model = HomeBroadcastListModel()

    override fun getData() {
        model.getData()
                .compose(RxUtils.defaultTransformer(view))
                .subscribe {
                    view.onGetData(it)
                }
    }

    override fun addTransitionAnimation(context: Activity, view: View) {
        model.addTransitionAnimation(context, view)
                .compose(RxUtils.defaultTransformer(view= this@HomeBroadcastListPresenter.view))
                .subscribe {
                    this@HomeBroadcastListPresenter.view.onGetBundle(it)
                }
    }

}