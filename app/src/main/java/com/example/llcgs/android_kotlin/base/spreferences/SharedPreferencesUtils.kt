package com.example.llcgs.android_kotlin.base.spreferences

/**
 * com.example.llcgs.android_kotlin.base.spreferences.SharedPreferencesUtils
 * @author liulongchao
 * @since 2017/8/31
 */


object SharedPreferencesUtils {

    fun setLoginName(name:String){
        SharedPreferencesHelper.sharedPreferences.edit().putString("loginName", name).apply()
    }

}