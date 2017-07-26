package com.example.llcgs.android_kotlin.list.view

import com.example.llcgs.android_kotlin.base.view.BaseView

/**
 * com.example.llcgs.android_kotlin.list.view.ListView
 * @author liulongchao
 * @since 2017/7/26
 */


interface ListView: BaseView {

    fun onGetListSuccess(list: List<String>)
}