package com.example.llcgs.android_kotlin.net.wifi.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import android.widget.Toast
import com.example.llcgs.android_kotlin.net.wifi.WiFiActivity
import com.gomejr.myf.core.kotlin.logD

/**
 * com.example.llcgs.android_kotlin.net.wifi.receiver.WiFiBroadcastReceiver
 * @author liulongchao
 * @since 2018/5/2
 */
class WiFiBroadcastReceiver(var manager: WifiP2pManager, var channel: WifiP2pManager.Channel, var activity: WiFiActivity): BroadcastReceiver(), WifiP2pManager.PeerListListener {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.d("MainActivity", "action: $action")
        when (action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                // 当设备的WiFi 直连接功能打开或关闭时进行广播
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                // 检查设置是否支持直连技术
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED){
                    // wifi 直连enable
                    Toast.makeText(activity, "该设备支持wifi直连技术", Toast.LENGTH_LONG).show()
                }else{
                    // wifi 直连disable
                    Toast.makeText(activity, "该设备不支持wifi直连技术", Toast.LENGTH_LONG).show()
                }
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                // 当调用discoverPeers()方法的时候进行广播，可以收到该消息，通常会调用requestPeers()获得设备列表的更新
            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                // 当设备的WiFi连接信息状态改变时候进行广播

            }
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                // 当设备的详细信息改变时进行广播，比如设备的名称
                Log.d("MainActivity", "channel: $channel")
                manager.requestPeers(channel, this@WiFiBroadcastReceiver)
            }
        }
    }


    override fun onPeersAvailable(peers: WifiP2pDeviceList) {
        peers.deviceList.size.logD()
        Log.d("MainActivity", "size: "+ peers.deviceList.size)
        onGetWifiP2pDeviceList?.let {
            it(peers)
        }
    }

    private var onGetWifiP2pDeviceList: ((peers: WifiP2pDeviceList) -> Unit)? = null

    fun setOnGetWifiP2pDeviceList(listener: (peers: WifiP2pDeviceList)->Unit){
        this.onGetWifiP2pDeviceList = listener
    }

}