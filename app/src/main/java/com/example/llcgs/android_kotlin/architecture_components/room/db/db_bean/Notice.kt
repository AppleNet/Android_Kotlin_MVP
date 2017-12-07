package com.example.llcgs.android_kotlin.architecture_components.room.db.db_bean

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.bean.Notice
 * @author liulongchao
 * @since 2017/12/6
 */

@Entity
class Notice {

    @PrimaryKey(autoGenerate = true)
    var uid = 0

    @ColumnInfo(name = "first_name")
    var firstName = ""

    @ColumnInfo(name = "last_name")
    var lastName = ""

}