package com.example.llcgs.android_kotlin.base.app

import android.app.Application
import com.example.llcgs.android_kotlin.utils.BaseUtil

/**
 * com.example.llcgs.android_kotlin.base.app.KotlinApplication
 * @author liulongchao
 * @since 2017/7/26
 */


class KotlinApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        BaseUtil.init(this)
    }
}