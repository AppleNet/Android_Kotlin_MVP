package com.example.llcgs.android_kotlin.net.wifi

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.os.Looper
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.Toast
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.net.base.BaseNetWorkActivity
import com.example.llcgs.android_kotlin.net.wifi.adapter.DeviceAdapter
import com.example.llcgs.android_kotlin.net.wifi.adapter.WiFiAdapter
import com.example.llcgs.android_kotlin.net.wifi.presenter.IWiFiPresenter
import com.example.llcgs.android_kotlin.net.wifi.presenter.impl.WiFiPresenter
import com.example.llcgs.android_kotlin.net.wifi.receiver.WiFiBroadcastReceiver
import com.example.llcgs.android_kotlin.net.wifi.view.WiFiView
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_wifi.*

/**
 * com.example.llcgs.android_kotlin.net.wifi.WiFiActivity
 * @author liulongchao
 * @since 2018/4/27
 */
class WiFiActivity: BaseNetWorkActivity<IWiFiPresenter>(), WiFiView, WifiP2pManager.ActionListener,
        (WifiP2pDeviceList) -> Unit {

    private lateinit var adapter: WiFiAdapter
    private lateinit var deviceAdapter: DeviceAdapter
    private lateinit var manager: WifiP2pManager
    private lateinit var channel: WifiP2pManager.Channel
    private lateinit var mReceiver: WiFiBroadcastReceiver
    private lateinit var mIntentFilter: IntentFilter


    override fun createPresenter(): IWiFiPresenter= WiFiPresenter(this)

    override fun getLayoutId()= R.layout.activity_wifi

    override fun initViews() {
        adapter = WiFiAdapter()
        deviceAdapter = DeviceAdapter()
        val array = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayout.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = manager
        RxPermissions(this)
            .request(*array)
            .subscribe {
                if (it){
                    initWifiConnect()
                    mPresenter.getScanResult()
                }
            }
    }

    private fun initWifiConnect(){
        manager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        channel = manager.initialize(this, Looper.getMainLooper(), null)
        mReceiver = WiFiBroadcastReceiver(manager, channel, this)

        mIntentFilter = IntentFilter().apply {
            addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
        }

        mReceiver.setOnGetWifiP2pDeviceList(this)
        // 发现对等设备
        manager.discoverPeers(channel, this)

        registerReceiver(mReceiver, mIntentFilter)
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }

    override fun onSuccess() {
        // 发现对等设备，成功时的处理
        Toast.makeText(this, "发现对等设备，成功", Toast.LENGTH_LONG).show()
    }

    override fun onFailure(reason: Int) {
        // 发现对等设备，失败时的处理
        Toast.makeText(this, "发现对等设备，失败", Toast.LENGTH_LONG).show()
    }

    override fun invoke(peers: WifiP2pDeviceList) {
        // 获取发现设备的列表
        if (peers.deviceList.isNotEmpty()){
            deviceAdapter.data.clear()
            deviceAdapter.setNewData(peers.deviceList.toMutableList())
            recyclerView.adapter = deviceAdapter
        }else{
            Toast.makeText(this, "获取对等设备列表，成功，但是设备列表为空", Toast.LENGTH_LONG).show()
        }
    }

    override fun onGetScanResult(list: List<ScanResult>) {
        adapter.data.clear()
        adapter.setNewData(list)
        recyclerView.adapter = adapter
    }
}