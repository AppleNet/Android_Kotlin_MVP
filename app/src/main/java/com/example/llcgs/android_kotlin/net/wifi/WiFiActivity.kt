package com.example.llcgs.android_kotlin.net.wifi

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.net.wifi.ScanResult
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.net.base.BaseNetWorkActivity
import com.example.llcgs.android_kotlin.net.wifi.adapter.WiFiAdapter
import com.example.llcgs.android_kotlin.net.wifi.presenter.IWiFiPresenter
import com.example.llcgs.android_kotlin.net.wifi.presenter.impl.WiFiPresenter
import com.example.llcgs.android_kotlin.net.wifi.view.WiFiView
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_wifi.*

/**
 * com.example.llcgs.android_kotlin.net.wifi.WiFiActivity
 * @author liulongchao
 * @since 2018/4/27
 */
class WiFiActivity: BaseNetWorkActivity<IWiFiPresenter>(), WiFiView {

    private lateinit var adapter: WiFiAdapter

    override fun createPresenter(): IWiFiPresenter= WiFiPresenter(this)

    override fun getLayoutId()= R.layout.activity_wifi

    override fun initViews() {
        adapter = WiFiAdapter()
        val array = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayout.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = manager
        RxPermissions(this)
            .request(*array)
            .subscribe {
                if (it){
                    mPresenter.getScanResult()
                }
            }
    }

    override fun onGetScanResult(list: List<ScanResult>) {
        adapter.data.clear()
        adapter.setNewData(list)
        recyclerView.adapter = adapter
    }
}