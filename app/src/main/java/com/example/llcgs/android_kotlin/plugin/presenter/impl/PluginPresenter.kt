package com.example.llcgs.android_kotlin.plugin.presenter.impl

import com.example.llcgs.android_kotlin.base.presenter.BasePresenter
import com.example.llcgs.android_kotlin.plugin.model.PluginModel
import com.example.llcgs.android_kotlin.plugin.presenter.IPluginPresenter
import com.example.llcgs.android_kotlin.plugin.view.PluginView

/**
 * com.example.llcgs.android_kotlin.plugin.presenter.impl.PluginPresenter
 * @author liulongchao
 * @since 2017/8/29
 */


class PluginPresenter(val pluginView: PluginView):BasePresenter<PluginView>(), IPluginPresenter {

    val pluginModel = PluginModel()

    override fun getListData() {
        val list = pluginModel.getList()
        pluginView.onGetListSuccess(list)
    }
}