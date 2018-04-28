package com.example.llcgs.android_kotlin.net.wifi.adapter

import android.net.wifi.ScanResult
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R

/**
 * com.example.llcgs.android_kotlin.net.wifi.adapter.WiFiAdapter
 * @author liulongchao
 * @since 2018/4/28
 */
class WiFiAdapter: BaseQuickAdapter<ScanResult, BaseViewHolder>(R.layout.view_item_ry) {

    override fun convert(helper: BaseViewHolder, item: ScanResult) {
        helper.setText(R.id.textView4, item.SSID)
    }
}