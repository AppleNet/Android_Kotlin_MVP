package com.example.llcgs.android_kotlin.mvvm.viewstubs.viewmodel

import android.databinding.ObservableInt
import android.view.View
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.viewstubs.model.ViewStubModel
import com.example.llcgs.android_kotlin.mvvm.viewstubs.view.ViewStubView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.mvvm.viewstubs.viewmodel.ViewStubViewModel
 * @author liulongchao
 * @since 2017/11/20
 */
class ViewStubViewModel(private val resultView: ViewStubView): BaseViewModel() {

    var viewStubModel = ViewStubModel()
    var text = "展开"
    var progress = ObservableInt(View.GONE)

    fun textViewClick(view: View){
        // 回调吧
        Observable.just(ViewStubModel().apply {
            sub1 = "James"
            sub2 = "33"
            sub3 = "Cleveland"
        }).delay(2, TimeUnit.SECONDS)
                .doOnSubscribe{
                    progress.set(View.VISIBLE)
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    progress.set(View.GONE)
                    resultView.onGetViews(view, it)
                }
    }

}