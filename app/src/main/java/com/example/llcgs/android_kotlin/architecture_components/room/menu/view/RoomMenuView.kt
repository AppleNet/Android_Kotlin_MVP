package com.example.llcgs.android_kotlin.architecture_components.room.menu.view

import com.example.llcgs.android_kotlin.architecture_components.base.view.BaseArchView
import com.example.llcgs.android_kotlin.architecture_components.room.menu.db.MenuBean

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.menu.view.RoomMenuView
 * @author liulongchao
 * @since 2017/12/8
 */
interface RoomMenuView : BaseArchView {

    fun insertSuccess()
    fun deleteSuccess()
    fun getAllSuccess(list: List<MenuBean>)
    fun switchSuccess(list: List<MenuBean>)

}