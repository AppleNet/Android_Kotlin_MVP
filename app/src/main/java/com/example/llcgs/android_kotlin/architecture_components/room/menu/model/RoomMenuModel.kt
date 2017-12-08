package com.example.llcgs.android_kotlin.architecture_components.room.menu.model

import com.example.llcgs.android_kotlin.architecture_components.base.model.BaseArchModel
import com.example.llcgs.android_kotlin.architecture_components.room.basedb.database.AppDatabase
import com.example.llcgs.android_kotlin.architecture_components.room.menu.db.MenuBean
import com.example.llcgs.android_kotlin.utils.DBUtils
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.menu.model.RoomMenuModel
 * @author liulongchao
 * @since 2017/12/8
 */
class RoomMenuModel : BaseArchModel {

    private val menuDao = DBUtils.getDBService(AppDatabase::class.java)

    fun insert(list: List<MenuBean>): Observable<Unit> =
            Observable.fromIterable(list)
                    .map {
                        menuDao.menuDao().insert(it)
                    }

    fun getAll(): Flowable<List<MenuBean>> = menuDao.menuDao().getAll()

    fun delete(list: List<MenuBean>) : Observable<Unit> =
        Observable.fromIterable(list)
                .map {
                    menuDao.menuDao().deleteAll(it)
                }

    fun switch(list: List<String>): Observable<List<MenuBean>>{
        return Observable.just(list)
                .flatMap { t ->
                    val menuList = ArrayList<MenuBean>()
                    t.forEach {
                        menuList.add(MenuBean().apply { this.content = it })
                    }
                    Observable.just(menuList)
                }
    }
}
