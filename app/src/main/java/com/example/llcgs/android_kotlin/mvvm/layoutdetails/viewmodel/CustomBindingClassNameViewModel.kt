package com.example.llcgs.android_kotlin.mvvm.layoutdetails.viewmodel

import android.content.Intent
import android.view.View
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.includes.IncludesActivity
import com.example.llcgs.android_kotlin.mvvm.layoutdetails.model.CustomUser

/**
 * com.example.llcgs.android_kotlin.mvvm.layoutdetails.viewmodel.CustomBindingClassNameViewModel
 * @author liulongchao
 * @since 2017/11/6
 */


class CustomBindingClassNameViewModel: BaseViewModel() {


    var customUser = CustomUser()

    fun textViewOnClickListener(view:View){
        //
        view.context.startActivity(Intent(view.context, IncludesActivity::class.java))
    }
}