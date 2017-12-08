package com.example.llcgs.android_kotlin.architecture_components.room.model

import com.example.llcgs.android_kotlin.architecture_components.base.model.BaseArchModel
import com.example.llcgs.android_kotlin.architecture_components.room.basedb.database.AppDatabase
import com.example.llcgs.android_kotlin.architecture_components.room.demo.db.bean.Notice
import com.example.llcgs.android_kotlin.utils.DBUtils
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.demo.RoomModel
 * @author liulongchao
 * @since 2017/12/6
 */
class RoomModel : BaseArchModel {

    private val noticeDao = DBUtils.getDBService(AppDatabase::class.java).noticeDao()

    fun getAll(): Flowable<List<Notice>> = noticeDao.getAll()

    fun input(input: CharSequence): Observable<Unit> = Observable.just(input)
            .map {
                noticeDao.insert(Notice().apply { firstName = "NBA"; lastName = it.toString() })
            }

    fun delete(notice: Notice): Observable<Unit> {
        return Observable.just(notice)
                .map {
                    noticeDao.delete(it)
                }
    }

    fun update(notice: Notice): Observable<Unit>{
        return Observable.just(notice)
                .map {
                    noticeDao.update(notice)
                }
    }

}