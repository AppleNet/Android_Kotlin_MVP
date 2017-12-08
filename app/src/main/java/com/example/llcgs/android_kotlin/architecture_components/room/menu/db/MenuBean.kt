package com.example.llcgs.android_kotlin.architecture_components.room.menu.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.menu.db.MenuBean
 * @author liulongchao
 * @since 2017/12/8
 */

@Entity(tableName = "menuBean")
class MenuBean {

    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "content")
    var content = ""

}