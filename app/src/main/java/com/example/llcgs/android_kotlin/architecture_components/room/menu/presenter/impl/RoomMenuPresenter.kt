package com.example.llcgs.android_kotlin.architecture_components.room.menu.presenter.impl

import com.example.llcgs.android_kotlin.architecture_components.base.rx.LifeCycleRx
import com.example.llcgs.android_kotlin.architecture_components.room.menu.db.MenuBean
import com.example.llcgs.android_kotlin.architecture_components.room.menu.model.RoomMenuModel
import com.example.llcgs.android_kotlin.architecture_components.room.menu.presenter.IRoomMenuPresenter
import com.example.llcgs.android_kotlin.architecture_components.room.menu.view.RoomMenuView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.menu.presenter.impl.RoomMenuPresenter
 * @author liulongchao
 * @since 2017/12/8
 */
class RoomMenuPresenter(private val view: RoomMenuView) : IRoomMenuPresenter {

    private val model = RoomMenuModel()

    override fun insert(list: List<MenuBean>) {
        model.insert(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(LifeCycleRx.bindLifecycle(view))
                .doOnSubscribe {
                    view.addDisposable(it)
                    view.showLoadingDialog()
                }
                .doOnTerminate {
                    view.dismissLoadingDialog()
                }
                .subscribe {
                    view.insertSuccess()
                }
    }

    override fun getAll() {
        model.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.showLoadingDialog()
                }
                .doOnTerminate {
                    view.dismissLoadingDialog()
                }
                .subscribe {
                    view.getAllSuccess(it)
                }
    }

    override fun deleteAll(list: List<MenuBean>) {
        model.delete(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.deleteSuccess()
                }
    }

    override fun switch(list: List<String>) {
        model.switch(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(LifeCycleRx.bindLifecycle(view))
                .doOnSubscribe {
                    view.addDisposable(it)
                    view.showLoadingDialog()
                }
                .doOnTerminate {
                    view.dismissLoadingDialog()
                }
                .subscribe {
                    view.switchSuccess(it)
                }
    }
}