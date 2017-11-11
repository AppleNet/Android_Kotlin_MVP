package com.example.llcgs.android_kotlin.replugin.pluginlist.view

import com.example.llcgs.android_kotlin.base.view.BaseView

/**
 * com.example.llcgs.android_kotlin.pluginlist.view.PluginListView
 * @author liulongchao
 * @since 2017/8/30
 */


interface PluginListView:BaseView {
    fun onGetListSuccess(list: List<String>)
}