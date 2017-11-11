package com.example.llcgs.android_kotlin.mvvm.menu.viewmodel

import android.content.Context
import android.databinding.ObservableInt
import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.menu.model.Menu
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.mvvm.menu.viewmodel.MenuViewModel
 * @author liulongchao
 * @since 2017/11/11
 */


class MenuViewModel: BaseViewModel() {

    val progress : ObservableInt = ObservableInt(View.GONE)
    var list = ArrayList<Menu>()

    fun fetchMenuList(context: Context){
        val array = context.resources.getStringArray(R.array.databinding_menu)
        Observable.just(array)
                .delay(2, TimeUnit.SECONDS)
                .doOnSubscribe {
                    progress.set(View.VISIBLE)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { p0 ->
                    val list = ArrayList<Menu>()
                    var menu:Menu
                    p0.forEach {
                        menu = Menu().apply {
                            title = it
                        }
                        list.add(menu)
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