package com.example.llcgs.android_kotlin.mvvm.attributesetters.viewmodel

import android.databinding.ObservableInt
import android.view.View
import com.example.llcgs.android_kotlin.mvvm.attributesetters.model.ContentModel
import com.example.llcgs.android_kotlin.mvvm.attributesetters.model.MenuAdapterModel
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.mvvm.attributesetters.viewmodel.AttributeSettersViewModel
 * @author liulongchao
 * @since 2017/11/22
 */
class AttributeSettersViewModel :BaseViewModel() {

    var list = ArrayList<MenuAdapterModel>()
    val progress = ObservableInt(View.GONE)
    var contentModel = ContentModel()

    fun fetchMenuList(array: Array<String>){
        Observable.just(array)
                .delay(1, TimeUnit.SECONDS)
                .doOnSubscribe {
                    progress.set(View.VISIBLE)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { p0 ->
                    val list = ArrayList<MenuAdapterModel>()
                    var menuAdapterModel: MenuAdapterModel
                    p0.forEach {
                        menuAdapterModel = MenuAdapterModel().apply {
                            title = it
                        }
                        list.add(menuAdapterModel)
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

    fun fetchContentList(model: MenuAdapterModel){

    }
}