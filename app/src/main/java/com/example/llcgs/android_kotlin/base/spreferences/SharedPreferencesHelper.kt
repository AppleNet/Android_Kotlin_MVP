package com.example.llcgs.android_kotlin.base.spreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.llcgs.android_kotlin.utils.BaseUtil

/**
 * com.example.llcgs.android_kotlin.base.spreferences.SharedPreferencesHelper
 * @author liulongchao
 * @since 2017/8/31
 */


object SharedPreferencesHelper {

    val sharedPreferences: SharedPreferences = BaseUtil.context().getSharedPreferences("APPLICATION_SP", Context.MODE_PRIVATE)

}