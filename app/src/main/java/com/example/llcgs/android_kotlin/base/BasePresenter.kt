package com.example.llcgs.android_kotlin.base

import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * com.example.llcgs.android_kotlin.base.BasePresenter
 * @author liulongchao
 * @since 2017/5/18
 */


open class BasePresenter<V> {


    lateinit var views:Reference<V>

    fun isAttacthView():Boolean{
        return views != null && views.get() != null
    }

    fun attatchView(view:V){
        views = WeakReference<V>(view);
    }

    fun detachView(){
        if (views != null){
            views.clear()
        }
    }

    fun getView():V ?{
        return if (views != null) views.get() as V else null
    }

}