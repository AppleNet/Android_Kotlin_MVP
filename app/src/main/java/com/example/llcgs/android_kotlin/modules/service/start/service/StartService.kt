package com.example.llcgs.android_kotlin.modules.service.start.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.gomejr.myf.core.kotlin.logD

/**
 * com.example.llcgs.android_kotlin.modules.service.start.service.StartService
 * @author liulongchao
 * @since 2018/3/21
 */
class StartService: Service() {

    override fun onCreate() {
        super.onCreate()
        "StartService--onCreate".logD()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        "StartService--onStartCommand".logD()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        "StartService--onDestroy".logD()

    }

    override fun onBind(intent: Intent?): IBinder? = null
}