package com.example.llcgs.android_kotlin.material.detail.presenter.impl

import com.example.llcgs.android_kotlin.base.rx.RxUtils
import com.example.llcgs.android_kotlin.material.detail.model.DetailModel
import com.example.llcgs.android_kotlin.material.detail.presenter.IDetailPresenter
import com.example.llcgs.android_kotlin.material.detail.view.DetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.material.detail.presenter.impl.DetailPresenter
 * @author liulongchao
 * @since 2017/12/14
 */
class DetailPresenter(private val view: DetailView) : IDetailPresenter {

    private val model = DetailModel()

    override fun loadComments() {
        model.loadComments()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    view.addDisposable(it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate {
                    //
                    view.dismissLoadingDialog()
                }
                .subscribe {
                    view.onGetComments(it)
                }
    }
}