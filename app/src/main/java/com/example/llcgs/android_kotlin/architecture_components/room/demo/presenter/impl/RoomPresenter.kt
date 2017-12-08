package com.example.llcgs.android_kotlin.architecture_components.room.presenter.impl

import com.afollestad.materialdialogs.MaterialDialog
import com.example.llcgs.android_kotlin.architecture_components.base.rx.LifeCycleRx
import com.example.llcgs.android_kotlin.architecture_components.room.demo.db.bean.Notice
import com.example.llcgs.android_kotlin.architecture_components.room.model.RoomModel
import com.example.llcgs.android_kotlin.architecture_components.room.presenter.IRoomPresenter
import com.example.llcgs.android_kotlin.architecture_components.room.view.RoomView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.demo.RoomPresenter
 * @author liulongchao
 * @since 2017/12/6
 */
class RoomPresenter(private val view: RoomView) : IRoomPresenter {

    private val model = RoomModel()

    override fun getAll() {
        model.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.onGetAll(it)
                }
    }

    override fun input(dialog: MaterialDialog, input: CharSequence) {
        model.input(input)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.addDisposable(it)
                    view.showLoadingDialog()
                }
                .compose(LifeCycleRx.bindLifecycle(view))
                .doOnTerminate {
                    view.dismissLoadingDialog()
                }
                .subscribe {
                    view.onInputSuccess()
                }
    }

    override fun delete(notice: Notice) {
        model.delete(notice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.addDisposable(it)
                    view.showLoadingDialog()
                }
                .compose(LifeCycleRx.bindLifecycle(view))
                .doOnTerminate {
                    view.dismissLoadingDialog()
                }
                .subscribe {
                    view.onDeleteSuccess()
                }

    }

    override fun update(notice: Notice) {
        model.update(notice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.addDisposable(it)
                    view.showLoadingDialog()
                }
                .compose(LifeCycleRx.bindLifecycle(view))
                .doOnTerminate {
                    view.dismissLoadingDialog()
                }
                .subscribe {
                    view.onUpdateSuccess()
                }
    }
}