package com.example.llcgs.android_kotlin.mvvm.collections.textwatcher

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * com.example.llcgs.android_kotlin.mvvm.commfeatures.textwatcher.OnlyOneTextWatcher
 * @author liulongchao
 * @since 2017/11/13
 */


class OnlyOneTextWatcher(private val editText: EditText): TextWatcher {


    /**
     * Editable s：改变后的最终文本
     *
     * */
    override fun afterTextChanged(s: Editable) {
        if (s.length >= 2){
            val substring = s.substring(1)
            editText.setText(substring)
            editText.setSelection(1)
        }
    }

    /**
     *
        CharSequence s：文本改变之前的内容
        int start：文本开始改变时的起点位置，从0开始计算
        int count：要被改变的文本字数，即将要被替代的选中文本字数
        int after：改变后添加的文本字数，即替代选中文本后的文本字数

        在当前文本s中，从start位置开始之后的count个字符（即将）要被after个字符替换掉

     * */
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    /**
        CharSequence s：文本改变之后的内容
        int start：文本开始改变时的起点位置，从0开始计算
        int before：要被改变的文本字数，即已经被替代的选中文本字数
        int count：改变后添加的文本字数，即替代选中文本后的文本字数

        在当前文本s中，从start位置开始之后的before个字符（已经）被count个字符替换掉了

     * */
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //
    }
}