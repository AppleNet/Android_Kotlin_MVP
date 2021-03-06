package com.example.llcgs.android_kotlin.modules.service.intentservice.service

import android.app.IntentService
import android.content.Intent
import com.example.llcgs.android_kotlin.utils.log.logD

/**
 * com.example.llcgs.android_kotlin.modules.service.intentservice.service.MyIntentService
 * @author liulongchao
 * @since 2018/3/22
 */
class MyIntentService: IntentService("MyIntentService") {


    override fun onHandleIntent(intent: Intent) {
        // 进行耗时操作。
        val url = intent.getStringExtra("down")
        url.logD()
        url.forEach {
            Thread.sleep(1000)
            it.logD()
        }
    }
}