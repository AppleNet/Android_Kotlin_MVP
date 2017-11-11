package com.example.llcgs.android_kotlin.mvvm.includes.viewmodel

import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.includes.model.Footer
import com.example.llcgs.android_kotlin.mvvm.includes.model.Header

/**
 * com.example.llcgs.android_kotlin.mvvm.includes.viewmodel.IncludesViewModel
 * @author liulongchao
 * @since 2017/11/10
 */


class IncludesViewModel: BaseViewModel() {

    //
    var header: Header = Header()

    var footer: Footer = Footer()

}