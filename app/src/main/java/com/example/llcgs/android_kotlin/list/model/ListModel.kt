package com.example.llcgs.android_kotlin.list.model

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.model.BaseModel
import com.example.llcgs.android_kotlin.utils.BaseUtil

/**
 * com.example.llcgs.android_kotlin.list.model.ListModel
 * @author liulongchao
 * @since 2017/7/26
 */


class ListModel: BaseModel {

    fun getList(): List<String>{
        val stringArray = BaseUtil.context().resources.getStringArray(R.array.list_array)
        return stringArray.toList()
    }

}