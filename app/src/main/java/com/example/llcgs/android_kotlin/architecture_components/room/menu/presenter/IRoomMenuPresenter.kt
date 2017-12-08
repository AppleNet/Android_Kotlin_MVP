package com.example.llcgs.android_kotlin.architecture_components.room.menu.presenter

import com.example.llcgs.android_kotlin.architecture_components.base.presenter.BaseArchPresenter
import com.example.llcgs.android_kotlin.architecture_components.room.menu.db.MenuBean

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.menu.presenter.IRoomMenuPresenter
 * @author liulongchao
 * @since 2017/12/8
 */
interface IRoomMenuPresenter : BaseArchPresenter {

    fun insert(list: List<MenuBean>)

    fun getAll()

    fun deleteAll(list: List<MenuBean>)

    fun switch(list: List<String>)

}