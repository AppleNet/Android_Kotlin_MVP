package com.example.llcgs.android_kotlin.mvvm.generatedbinding.viewmodel

import android.content.Context
import android.databinding.ObservableInt
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.generatedbinding.model.GeneratedItemModel
import com.example.llcgs.android_kotlin.mvvm.layoutdetails.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.mvvm.generatedbinding.viewmodel.GeneratedBindingViewModel
 * @author liulongchao
 * @since 2017/11/17
 */
class GeneratedBindingViewModel : BaseViewModel() {

    // 构建数据
    var list = ArrayList<GeneratedItemModel>()
    var progress = ObservableInt(View.GONE)

    fun fetchList(context: Context){
        val array = context.resources.getStringArray(R.array.databinding_nba)
        Observable.just(array)
                .delay(2, TimeUnit.SECONDS)
                .doOnSubscribe {
                    progress.set(View.VISIBLE)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { p0 ->
                    val list = ArrayList<GeneratedItemModel>()
                    var generatedItemModel: GeneratedItemModel
                    p0.forEach {
                        generatedItemModel = GeneratedItemModel().apply {
                            title = it
                        }
                        list.add(generatedItemModel)
                    }
                    Observable.just(list)
                }
                .subscribe {
                    progress.set(View.GONE)
                    list = it
                    setChanged()
                    notifyObservers()
                }

    }


}