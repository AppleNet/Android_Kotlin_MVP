package com.example.llcgs.android_kotlin.mvvm.includes.model

import com.example.llcgs.android_kotlin.utils.log.logD

/**
 * com.example.llcgs.android_kotlin.mvvm.includes.model.Header
 * @author liulongchao
 * @since 2017/11/10
 */


class Header{

    var headerTitle:String = ""
        get() {
            "field: $field".logD()
            return field
        }

    var headerContent:String = ""
        get() {
            "field: $field".logD()
            return field
        }

}