package com.example.llcgs.android_kotlin.mvvm.observablecollections.viewmodel

import android.view.View
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.observablecollections.view.ObservableCollectionsView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.mvvm.observablecollections.viewmodel.ObservableCollectionsViewModel
 * @author liulongchao
 * @since 2017/11/16
 */
class ObservableCollectionsViewModel(private val collectionsView: ObservableCollectionsView) :BaseViewModel() {

    private var indexArray = arrayListOf(0, 1, 2, 3)
    private var indexArrayLength = indexArray.size.toLong()

    private var keyArray = arrayListOf("0", "1", "2", "3")
    private var keyArrayLength = keyArray.size.toLong()

    private var itemArray = arrayListOf("Jordon", "O'Neal", "Garnett", "Durant", "Bosh", "Paul", "Harden", "Curry")

    fun buttonClick(view:View){
        // 改变index 和 key
//        Observable.fromIterable(indexArray)
//                .delay(1, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe{
//                    collectionsView.onGetIndex(it)
//                }
        // TODO vararg 可变参数，要想传递进去一个数组 需要在前边加*号
//        Observable.fromArray(*arrayOf("0", "1", "2", "3"))
//                .delay(1, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe{
//                    collectionsView.onGetKey(it)
//                }


        Observable.interval(1,TimeUnit.SECONDS)
                .take(indexArrayLength)
                .repeat()
                .compose(bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
                .doOnSubscribe{
                    addDisposable(it)
                    // 还可以showLoading
                }
                .subscribeOn(Schedulers.io())
                .map {
                    indexArray[it.toInt()]
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    collectionsView.onGetIndex(it)
                }

        Observable.interval(1, TimeUnit.SECONDS)
                .take(keyArrayLength)
                .repeat()
                .compose(bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
                .doOnSubscribe{
                    addDisposable(it)
                }
                .subscribeOn(Schedulers.io())
                .map {
                    keyArray[it.toInt()]
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    collectionsView.onGetKey(it)
                }


    }

    fun buttonClickAdd(view:View){

        indexArray.add(4)
        indexArray.add(5)
        indexArray.add(6)
        indexArray.add(7)
        indexArray.add(8)
        indexArray.add(9)
        indexArray.add(10)
        indexArray.add(11)

        Observable.just(itemArray)
                .compose(bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
                .doOnSubscribe{
                    addDisposable(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    collectionsView.onGetList(it)
                }
    }

}