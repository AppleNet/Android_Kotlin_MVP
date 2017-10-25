package com.example.llcgs.android_kotlin.mvvm.viewmodel

import android.view.View
import android.widget.Toast
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel

/**
 * com.example.llcgs.android_kotlin.mvvm.viewmodel.HomeViewModel
 * @author liulongchao
 * @since 2017/10/20
 */


class HomeViewModel: BaseViewModel() {

    @Command
    fun onClickLoadData() = View.OnClickListener {
        Toast.makeText(it.context, "MvvM Toast", Toast.LENGTH_LONG).show()
    }

}