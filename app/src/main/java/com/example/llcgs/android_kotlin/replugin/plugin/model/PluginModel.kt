package com.example.llcgs.android_kotlin.replugin.plugin.model

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.model.BaseModel
import com.example.llcgs.android_kotlin.utils.BaseUtil

/**
 * com.example.llcgs.android_kotlin.plugin.model.PluginModel
 * @author liulongchao
 * @since 2017/8/29
 */


class PluginModel:BaseModel {

    fun getList(): List<String>{
        val stringArray = BaseUtil.context().resources.getStringArray(R.array.plugin_array)
        return stringArray.toList()
    }

}