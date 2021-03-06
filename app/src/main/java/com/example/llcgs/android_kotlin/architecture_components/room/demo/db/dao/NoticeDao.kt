package com.example.llcgs.android_kotlin.architecture_components.room.demo.db.dao

import android.arch.persistence.room.*
import com.example.llcgs.android_kotlin.architecture_components.room.basedb.dao.BaseDAO
import com.example.llcgs.android_kotlin.architecture_components.room.demo.db.bean.Notice
import io.reactivex.Flowable

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.dao.NoticeDao
 * @author liulongchao
 * @since 2017/12/6
 */
@Dao
interface NoticeDao : BaseDAO {

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notice: Notice)

    @Delete
    fun delete(notice: Notice)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(notice: Notice)

}