@file:Suppress("FunctionName")
package com.alibaba.android.kotlin

import android.content.Context
import android.text.InputType
import com.afollestad.materialdialogs.MaterialDialog

/**
 * com.alibaba.android.kotlin.ActivityWrapper
 * @author liulongchao
 * @since 2017/12/7
 */

fun showInputMaterialDialog(context: Context, title: String = "MaterialDialog", content: String = "Input your notice here", callback: MaterialDialog.InputCallback): MaterialDialog {
    return MaterialDialog.Builder(context)
            .title("MaterialDialog")
            .content("Input your notice here")
            .inputType(InputType.TYPE_CLASS_TEXT)
            .input("firstName input here", "", callback).show()
}

fun showMessageMaterialDialog(context: Context, title: String = "MaterialDialog", content: String = "Input your notice here", callback: MaterialDialog.SingleButtonCallback): MaterialDialog {
    return MaterialDialog.Builder(context)
            .title(title)
            .content(content)
            .positiveText("同意")
            .negativeText("取消")
            .onPositive(callback)
            .show()
}

fun showProgressMaterialDialog(context: Context, title: String = "MaterialDialog", content: String = "Input your notice here"): MaterialDialog {
    return MaterialDialog.Builder(context)
            .title(title)
            .content(content)
            .progress(true, 0)
            .show()
}

fun showCustomMaterialDialog(context: Context, title: String = "MaterialDialog", content: String = "Input your notice here"){

}