package com.example.llcgs.android_kotlin.architecture_components.room.menu.db

import android.arch.persistence.room.*
import com.example.llcgs.android_kotlin.architecture_components.room.basedb.dao.BaseDAO
import io.reactivex.Flowable

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.menu.db.MenuDAO
 * @author liulongchao
 * @since 2017/12/8
 */
@Dao
interface MenuDAO : BaseDAO {

    // insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(menuBean: MenuBean)

    @Query("select * from menuBean")
    fun getAll(): Flowable<List<MenuBean>>

    @Delete
    fun deleteAll(menuBean: MenuBean)

}