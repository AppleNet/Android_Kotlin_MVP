package com.example.llcgs.android_kotlin.architecture_components.room.demo.db.bean

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.llcgs.android_kotlin.architecture_components.room.basedb.db_bean.BaseBean

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.bean.Notice
 * @author liulongchao
 * @since 2017/12/6
 */

@Entity
class Notice : BaseBean {

    @PrimaryKey(autoGenerate = true)
    var uid = 0

    @ColumnInfo(name = "first_name")
    var firstName = ""

    @ColumnInfo(name = "last_name")
    var lastName = ""

}