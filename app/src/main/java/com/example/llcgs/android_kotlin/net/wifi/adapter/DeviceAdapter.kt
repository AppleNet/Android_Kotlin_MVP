package com.example.llcgs.android_kotlin.net.wifi.adapter

import android.net.wifi.p2p.WifiP2pDevice
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R

/**
 * com.example.llcgs.android_kotlin.net.wifi.adapter.DeviceAdapter
 * @author liulongchao
 * @since 2018/5/2
 */
class DeviceAdapter: BaseQuickAdapter<WifiP2pDevice, BaseViewHolder>(R.layout.view_item_ry) {

    override fun convert(helper: BaseViewHolder, item: WifiP2pDevice) {
        helper.setText(R.id.textView4, item.deviceName)
    }
}