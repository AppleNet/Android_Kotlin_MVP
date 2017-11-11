package com.example.llcgs.android_kotlin.replugin.pluginlist.model

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.model.BaseModel
import com.example.llcgs.android_kotlin.utils.BaseUtil

/**
 * com.example.llcgs.android_kotlin.pluginlist.model.PluginListModel
 * @author liulongchao
 * @since 2017/8/30
 */


class PluginListModel:BaseModel {

    fun getList(): List<String>{
        val stringArray = BaseUtil.context().resources.getStringArray(R.array.plugin_demo)
        return stringArray.toList()
    }
}