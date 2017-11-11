package com.example.llcgs.android_kotlin.mvvm.commfeatures.viewmodel

import android.databinding.ObservableField
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel

/**
 * com.example.llcgs.android_kotlin.mvvm.commfeatures.viewmodel.CommonFeaturesViewModel
 * @author liulongchao
 * @since 2017/11/11
 */


class CommonFeaturesViewModel:BaseViewModel() {

    /**
     *  Common Features
     *
        Mathematical + - / * %
        String concatenation +
        Logical && ||
        Binary & | ^
        Unary + - ! ~
        Shift >> >>> <<
        Comparison == > < >= <=
        instanceof
        Grouping ()
        Literals - character, String, numeric, null
        Cast
        Method calls
        Field access
        Array access []
        Ternary operator ?:

     * */
    var numberTwo = ObservableField<String>("1")
    var numberOne = ObservableField<String>("1")

}