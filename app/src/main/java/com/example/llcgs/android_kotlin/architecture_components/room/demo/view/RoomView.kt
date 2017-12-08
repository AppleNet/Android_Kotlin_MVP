package com.example.llcgs.android_kotlin.architecture_components.room.view

import com.example.llcgs.android_kotlin.architecture_components.base.view.BaseArchView
import com.example.llcgs.android_kotlin.architecture_components.room.demo.db.bean.Notice

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.demo.RoomView
 * @author liulongchao
 * @since 2017/12/6
 */
interface RoomView : BaseArchView {

    fun onGetAll(data: List<Notice>)

    fun onInputSuccess()

    fun onDeleteSuccess()

    fun onUpdateSuccess()
}