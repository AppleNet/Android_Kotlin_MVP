package com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.ld

import android.arch.lifecycle.LiveData

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.ld.SecondsLiveData
 * @author liulongchao
 * @since 2017/11/29
 */
class SecondsLiveData() : LiveData<Long>() {

    private var onGetSeconds: OnGetSeconds? = null

    override fun onActive() {
        onGetSeconds = object : OnGetSeconds {
            override fun post(l: Long) {
                value = l
            }
        }
    }

    override fun onInactive() {
    }

    interface OnGetSeconds {
        fun post(l: Long)
    }

    fun postSecond(l: Long) {
        onGetSeconds?.post(l)
    }
}