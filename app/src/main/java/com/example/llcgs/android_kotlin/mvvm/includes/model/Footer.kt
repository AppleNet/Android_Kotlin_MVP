package com.example.llcgs.android_kotlin.mvvm.includes.model

import com.example.llcgs.android_kotlin.utils.log.logD

/**
 * com.example.llcgs.android_kotlin.mvvm.includes.model.Footer
 * @author liulongchao
 * @since 2017/11/10
 */


class Footer{

    var footerTitle = ""
        get() {
            "field: $field".logD()
            return field
        }
    var footerContent:String = ""
        get() {
            "field: $field".logD()
            return field
        }

}