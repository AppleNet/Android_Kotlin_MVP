package com.example.llcgs.android_kotlin.architecture_components.room.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.llcgs.android_kotlin.architecture_components.room.db.db_bean.Notice
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.dao.NoticeDao
 * @author liulongchao
 * @since 2017/12/6
 */

@Dao
interface NoticeDao {

    /**
     *  还得写sql语句 感觉有点恶心了
     *
     * */
    @Query("select * from notice")
    fun getAll(): Flowable<List<Notice>>

    @Query("select * from notice where uid in (:ids)")
    fun loadAllByIds(ids: Array<Int>): Flowable<List<Notice>>

    @Query("select * from notice where first_name like :first AND " +
            " last_name like :last limit 1")
    fun finAllByName(first: String, last: String): Flowable<Notice>

    @Insert
    fun insert(notice: Notice)

    @Delete
    fun delete(notice: Notice)

}