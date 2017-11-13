package com.example.llcgs.android_kotlin.mvvm.commfeatures.filters

import android.text.InputFilter
import android.text.Spanned

/**
 * com.example.llcgs.android_kotlin.mvvm.commfeatures.filters.OnlyOneFilter
 * @author liulongchao
 * @since 2017/11/13
 */


class OnlyOneFilter(): InputFilter {


    /**
     *  source    新输入的字符串
     *  start     新输入的字符串起始下标，一般为0
     *  end       新输入的字符串终点下标，一般为source长度-1
     *  dest      输入之前文本框内容
     *  dstart    原内容起始坐标，一般为0
     *  dend      原内容终点坐标，一般为dest长度-1
     *
     * */
    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence {
        return source
    }
}