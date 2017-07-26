package com.example.llcgs.android_kotlin.list.presenter.impl

import com.example.llcgs.android_kotlin.base.presenter.BasePresenter
import com.example.llcgs.android_kotlin.list.model.ListModel
import com.example.llcgs.android_kotlin.list.presenter.IListPresenter
import com.example.llcgs.android_kotlin.list.view.ListView

/**
 * com.example.llcgs.android_kotlin.list.presenter.impl.ListPresenter
 * @author liulongchao
 * @since 2017/7/26
 */


class ListPresenter(val listView: ListView): BasePresenter<ListView>(), IListPresenter {

    val listModel = ListModel()

    override fun getListData() {
        val list = listModel.getList()
        listView.onGetListSuccess(list)
    }
}