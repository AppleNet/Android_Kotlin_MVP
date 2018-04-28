package com.example.llcgs.android_kotlin.net.wifi.view

import android.net.wifi.ScanResult
import com.example.llcgs.android_kotlin.net.base.BaseNetWorkView

/**
 * com.example.llcgs.android_kotlin.net.wifi.view.WiFiView
 * @author liulongchao
 * @since 2018/4/27
 */
interface WiFiView: BaseNetWorkView {

    fun onGetScanResult(list: List<ScanResult>)
}