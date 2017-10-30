package com.example.llcgs.android_kotlin.mvvm.login.viewmodel

import android.view.View
import android.widget.Toast
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.login.model.User
import com.example.llcgs.android_kotlin.mvvm.login.view.MvvmView

/**
 * com.example.llcgs.android_kotlin.mvvm.login.viewmodel.HomeViewModel
 * @author liulongchao
 * @since 2017/10/20
 */

class HomeViewModel(val view: MvvmView): BaseViewModel() {

    val user = User()

    @Command
    fun onClickLoadData() = View.OnClickListener {
        if (user.userName.equals("Kobe", true)   && user.userPwd == "37"){
            view.onLoginSuccess(user)
        }else{
            Toast.makeText(it.context, "MvvM Toast", Toast.LENGTH_LONG).show()
        }
    }

}