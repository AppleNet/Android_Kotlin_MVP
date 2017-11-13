package com.example.llcgs.android_kotlin.mvvm.collections.viewmodel

import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel

/**
 * com.example.llcgs.android_kotlin.mvvm.collections.viewmodel.CollectionsViewModel
 * @author liulongchao
 * @since 2017/11/11
 */


class CollectionsViewModel:BaseViewModel() {

    var indexString = "0"
    set(value) {
        field = value
        setChanged()
        notifyObservers()
    }
}