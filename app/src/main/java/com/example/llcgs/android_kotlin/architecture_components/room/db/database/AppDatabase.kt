package com.example.llcgs.android_kotlin.architecture_components.room.db.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.llcgs.android_kotlin.architecture_components.room.db.db_bean.Notice
import com.example.llcgs.android_kotlin.architecture_components.room.db.dao.NoticeDao

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.database.AppDatabase
 * @author liulongchao
 * @since 2017/12/6
 */

@Database(entities = [(Notice::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noticeDao(): NoticeDao
}