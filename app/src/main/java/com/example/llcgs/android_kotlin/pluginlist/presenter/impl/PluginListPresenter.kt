package com.example.llcgs.android_kotlin.pluginlist.presenter.impl

import com.example.llcgs.android_kotlin.base.presenter.BasePresenter
import com.example.llcgs.android_kotlin.pluginlist.model.PluginListModel
import com.example.llcgs.android_kotlin.pluginlist.presenter.IPluginListPresenter
import com.example.llcgs.android_kotlin.pluginlist.view.PluginListView

/**
 * com.example.llcgs.android_kotlin.pluginlist.presenter.impl.PluginListPresenter
 * @author liulongchao
 * @since 2017/8/30
 */


class PluginListPresenter(val pluginListView: PluginListView):BasePresenter<PluginListView>(), IPluginListPresenter {

    val pluginListModel = PluginListModel()

    override fun getListData() {
        val list = pluginListModel.getList()
        pluginListView.onGetListSuccess(list)
    }
}