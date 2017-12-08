package com.example.llcgs.android_kotlin.utils

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase

/**
 * com.example.llcgs.android_kotlin.utils.DBUtils
 * @author liulongchao
 * @since 2017/12/6
 */
object DBUtils {

    fun <T : RoomDatabase> getDBService(service: Class<T>, dbName: String = "notice.db"): T =
            Room.databaseBuilder(BaseUtil.context(), service, dbName).build()

}