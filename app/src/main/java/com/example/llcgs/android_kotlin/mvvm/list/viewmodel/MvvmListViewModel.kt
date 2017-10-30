package com.example.llcgs.android_kotlin.mvvm.list.viewmodel

import com.example.llcgs.android_kotlin.base.network.BaseFactory
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.list.model.Student
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * com.example.llcgs.android_kotlin.mvvm.list.viewmodel.MvvmListViewModel
 * @author liulongchao
 * @since 2017/10/26
 */

class MvvmListViewModel : BaseViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var peopleList = ArrayList<Student>()

    fun fetchStudentList() {
        compositeDisposable.add(BaseFactory.create().fetchPeople(BaseFactory.RANDOM_USER_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    this.peopleList = it.peopleList as ArrayList<Student>
                    setChanged()
                    notifyObservers()
                })
    }
}