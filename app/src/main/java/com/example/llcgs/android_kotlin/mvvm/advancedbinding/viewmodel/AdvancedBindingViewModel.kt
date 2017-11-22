package com.example.llcgs.android_kotlin.mvvm.advancedbinding.viewmodel

import android.content.Context
import android.databinding.ObservableInt
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.mvvm.advancedbinding.model.AdvancedBindingModel
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.generatedbinding.model.GeneratedItemModel
import com.example.llcgs.android_kotlin.mvvm.layoutdetails.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.mvvm.advancedbinding.viewmodel.AdvancedBindingViewModel
 * @author liulongchao
 * @since 2017/11/20
 */
class AdvancedBindingViewModel: BaseViewModel() {

    var progress = ObservableInt(View.GONE)
    var list = ArrayList<AdvancedBindingModel>()

    fun fetchList(array: Array<String>){
        Observable.just(array)
                .delay(2, TimeUnit.SECONDS)
                .doOnSubscribe {
                    progress.set(View.VISIBLE)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { p0 ->
                    val list = ArrayList<AdvancedBindingModel>()
                    var generatedItemModel: AdvancedBindingModel
                    p0.forEach {
                        generatedItemModel = AdvancedBindingModel().apply {
                            title = it
                            sub1 = "McGrady"
                            sub2 = "37"
                            sub3 = "Houston"
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