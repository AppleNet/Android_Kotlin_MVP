package com.example.llcgs.android_kotlin.mvvm.list.viewmodel

import android.databinding.ObservableInt
import android.view.View
import com.example.llcgs.android_kotlin.base.network.RetrofitHelper
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

    val progress : ObservableInt = ObservableInt(View.VISIBLE)
    var peopleList = ArrayList<Student>()
    private val compositeDisposable = CompositeDisposable()

    fun fetchStudentList() {
        compositeDisposable.add(RetrofitHelper.getService().fetchPeople(RetrofitHelper.RANDOM_USER_BASE_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    progress.set(View.GONE)
                    this.peopleList = it.peopleList as ArrayList<Student>
                    setChanged()
                    notifyObservers()
                })
    }
}