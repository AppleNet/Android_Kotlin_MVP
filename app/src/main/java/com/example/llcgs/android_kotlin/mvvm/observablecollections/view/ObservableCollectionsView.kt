package com.example.llcgs.android_kotlin.mvvm.observablecollections.view

/**
 * com.example.llcgs.android_kotlin.mvvm.observablecollections.view.ObservableCollectionsView
 * @author liulongchao
 * @since 2017/11/16
 */
interface ObservableCollectionsView {

    fun onGetIndex(index : Int)
    fun onGetKey(key: String)

    fun onGetList(list:ArrayList<String>)
}