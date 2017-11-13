package com.example.llcgs.android_kotlin.mvvm.stringliterals.viewmodel

import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel

/**
 * com.example.llcgs.android_kotlin.mvvm.stringliterals.viewmodel.StringLiteralsViewModel
 * @author liulongchao
 * @since 2017/11/13
 */


class StringLiteralsViewModel:BaseViewModel() {

    var index: String = "firstName"
    set(value) {
        field = value
        setChanged()
        notifyObservers()
    }

}