package com.example.llcgs.android_kotlin.modules.service.bind.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.gomejr.myf.core.kotlin.logD

/**
 * com.example.llcgs.android_kotlin.modules.service.bind.service.BindService
 * @author liulongchao
 * @since 2018/3/21
 */
class BindService: Service() {


    override fun onBind(intent: Intent?): IBinder {
        "BindService---onBind".logD()
        return MyBinder()
    }

    override fun onCreate() {
        super.onCreate()
        "BindService---onCreate".logD()
    }


    override fun onUnbind(intent: Intent?): Boolean {
        "BindService---onUnbind".logD()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        "BindService---onDestroy".logD()
    }

    class MyBinder: Binder(){
        fun log(){
            "BindService---MyBinder".logD()
        }
    }
}