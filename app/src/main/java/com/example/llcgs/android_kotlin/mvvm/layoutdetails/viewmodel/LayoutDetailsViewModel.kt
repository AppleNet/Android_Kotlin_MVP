package com.example.llcgs.android_kotlin.mvvm.layoutdetails.viewmodel

import android.content.Intent
import android.view.View
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.layoutdetails.CustomBindingClassNameActivity
import com.example.llcgs.android_kotlin.mvvm.layoutdetails.model.User

/**
 * com.example.llcgs.android_kotlin.mvvm.layoutdetails.viewmodel.LayoutDetailsViewModel
 * @author liulongchao
 * @since 2017/11/4
 */


class LayoutDetailsViewModel : BaseViewModel() {


    // Imports
    var user = User()

    fun nameOnClickListener(view: View){
        //
        view.context.startActivity(Intent(view.context, CustomBindingClassNameActivity::class.java))
    }

}