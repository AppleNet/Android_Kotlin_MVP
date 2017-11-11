package com.example.llcgs.android_kotlin.replugin.plugin.view

import com.example.llcgs.android_kotlin.base.view.BaseView

/**
 * com.example.llcgs.android_kotlin.plugin.view.PluginView
 * @author liulongchao
 * @since 2017/8/29
 */


interface PluginView:BaseView {
    fun onGetListSuccess(list: List<String>)
}