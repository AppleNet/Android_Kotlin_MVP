package com.example.llcgs.android_kotlin.mvvm.list.viewmodel

import android.databinding.ObservableField
import android.view.View
import android.widget.Toast
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.list.model.Student

/**
 * com.example.llcgs.android_kotlin.mvvm.list.viewmodel.ItemListViewModel
 * @author liulongchao
 * @since 2017/10/26
 */


class ItemListViewModel: BaseViewModel() {

    val imageUrl = ObservableField<String>()
    var student = Student()

    fun setStudents(student: Student){
        this.student = student
        imageUrl.set(student.picture.medium)
    }

    /*
    *  item 点击事件
    * */
    fun onItemClickListener(view: View){
        Toast.makeText(view.context, student.fullName, Toast.LENGTH_LONG).show()
    }

}