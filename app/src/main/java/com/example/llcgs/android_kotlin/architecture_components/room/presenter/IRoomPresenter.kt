package com.example.llcgs.android_kotlin.architecture_components.room.presenter

import com.afollestad.materialdialogs.MaterialDialog
import com.example.llcgs.android_kotlin.architecture_components.base.presenter.BaseArchPresenter
import com.example.llcgs.android_kotlin.architecture_components.room.db.db_bean.Notice

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.presenter.IRoomPresenter
 * @author liulongchao
 * @since 2017/12/6
 */
interface IRoomPresenter : BaseArchPresenter {

    fun getAll()

    fun input(dialog: MaterialDialog, input: CharSequence)

    fun delete(notice: Notice)

}