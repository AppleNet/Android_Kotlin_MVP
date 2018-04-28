package com.example.llcgs.android_kotlin.net.wifi.model

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import com.example.llcgs.android_kotlin.net.base.BaseNetWorkModel
import com.gomejr.myf.core.kotlin.logD
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.net.wifi.model.WiFiModel
 * @author liulongchao
 * @since 2018/4/27
 */
class WiFiModel (var context: Context) : BaseNetWorkModel {

    private val mStringBuffer = StringBuffer()
    // 保存扫描结果列表
    private var listResult: List<ScanResult> = ArrayList()
    // 创建ScanResult类
    private lateinit var mScanResult: ScanResult
    // 创建WifiManager类
    @SuppressLint("WifiManagerPotentialLeak")
    private var mWifiManager: WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    // 创建WifiInfo类
    private var mWifiInfo: WifiInfo? = null
    // 网络链接列表
    private var wifiConfigList: List<WifiConfiguration> = ArrayList<WifiConfiguration>()
    // 创建一个WifiLock
    private lateinit var mWifiLock: WifiManager.WifiLock
    // 创建链接管理器
    private lateinit var connManager: ConnectivityManager
    // WIFI配置列表
    private val wifiConfigedSpecifiedList = ArrayList<WifiConfiguration>()
    // 利用ArrayList类实例化List集合
    private lateinit var state: NetworkInfo.State
    // 定义是否已经连接
    private var isConnection: Boolean = false

    private val WIFICIPHER_NOPASS = 0
    private val WIFICIPHER_WEP = 1
    private val WIFICIPHER_WPA = 2

    init {
        mWifiInfo = mWifiManager.connectionInfo
        mWifiLock = mWifiManager.createWifiLock("LLC")
        connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    // 重新获取当前WIFI连接讯息
    fun againGetWifiInfo(){
        mWifiInfo = mWifiManager.connectionInfo
    }

    // 判断用户是否开启wifi网卡，true为开启
    fun isNetCardFriendly(): Observable<Boolean> = Observable.just(mWifiManager.isWifiEnabled)

    // 判断系统当前是否已经连接wifi，true表示当前已经连接wifi
    fun isConnecting(): Observable<Boolean>{
        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state
        return Observable.just(state == NetworkInfo.State.CONNECTING)
    }

    // 得到当前的网络连接状态
    fun getCurrentState(): Observable<NetworkInfo.State> = Observable.just(connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state)

    // 设置配置好的网络(有密码的网络并配置好了密码)，指定的
    fun setWifiConfigedSpecialfiedList(ssid: String){
        wifiConfigedSpecifiedList.clear()
        wifiConfigList.forEach {
            if (it.SSID.equals("\""+ssid+"\"", true) && it.preSharedKey != null){
                wifiConfigedSpecifiedList.add(it)
            }
        }
    }

    // 返回 WiFi 设置列表
    fun getWifiConfigedSpecialfiedList() = wifiConfigedSpecifiedList

    // 打开wifi网卡
    fun openNetCard(){
        if (!mWifiManager.isWifiEnabled){
            mWifiManager.isWifiEnabled = true
        }
    }

    // 关闭wifi网卡
    fun closeNetCard(){
        if (mWifiManager.isWifiEnabled){
            mWifiManager.isWifiEnabled = false
        }
    }

    // 检查当前wifi网卡状态
    fun checkNetCardState(){
        when(mWifiManager.wifiState){
            0 ->{
               Observable.just("网卡正在关闭")
            }
            1 ->{
                Observable.just("网卡已经关闭")
            }
            2 ->{
                Observable.just("网卡正在打开")
            }
            4 ->{
                Observable.just("网卡已经打开")
            }
            else ->{
                Observable.just("没有获取到状态")
            }
        }
    }

    // 扫描周边网络
    fun scan(): Observable<List<ScanResult>>{
        // 开始扫描
        val flag = mWifiManager.startScan()
        "flag $flag".logD()
        // 获取扫描结果
        listResult = mWifiManager.scanResults
        // 扫描配置列表
        wifiConfigList = mWifiManager.configuredNetworks
        return Observable.just(listResult)
    }

    fun getListResult() = if (listResult.isEmpty()) Observable.just(listResult) else Observable.just(ArrayList())

    fun getScanResult(): String{
        // 开始扫描网络
        scan()
        if (listResult.isNotEmpty()){
            for(i in 0 until listResult.size - 1){
                mScanResult = listResult[i]
                mStringBuffer.append("NO.")
                    .append(i + 1)
                    .append(" :")
                    .append(mScanResult.SSID)
                    .append("->")
                    .append(mScanResult.BSSID)
                    .append("->")
                    .append(mScanResult.capabilities)
                    .append("->")
                    .append(mScanResult.frequency)
                    .append("->")
                    .append(mScanResult.level)
                    .append("->")
                    .append(mScanResult.describeContents())
                    .append("\n\n")
            }
        }
        return mStringBuffer.toString()
    }

    // 连接指定wifi
    fun connect(ssid: String, passWord:String, type: Int): Observable<Boolean>{
        // createWifiConfig主要用于构建一个WifiConfiguration，代码中的例子主要用于连接不需要密码的Wifi
        // WifiManager的addNetwork接口，传入WifiConfiguration后，得到对应的NetworkId
        val netId = mWifiManager.addNetwork(createWifiConfig(ssid, passWord, type))
        // WifiManager的enableNetwork接口，就可以连接到netId对应的wifi了
        // 其中boolean参数，主要用于指定是否需要断开其它Wifi网络
        return Observable.just(mWifiManager.enableNetwork(netId, true))
    }

    //可选操作，让Wifi重新连接最近使用过的接入点
    //如果上文的enableNetwork成功，那么reconnect同样连接netId对应的网络
    //若失败，则连接之前成功过的网络
    fun reconnect(): Observable<Boolean> = Observable.just(mWifiManager.reconnect())

    // 断开指定wifi
    fun disConnectWifi(ssid : String): Observable<Boolean>{
        // 设置网络不可用
        if(mWifiManager.disableNetwork(isExist(ssid)?.networkId ?: 0)){
            // 断开网络
            mWifiInfo = null
            return Observable.just(mWifiManager.disconnect())
        }
        return Observable.just(false)

    }

    // 检查当前网络状态
    fun checkNetWorkState(): Observable<Boolean> = Observable.just(mWifiInfo != null)

    // 得到连接的ID
    fun getNetWorkId():Observable<Int> = Observable.just(mWifiInfo?.networkId ?: 0)

    // 得到IP地址
    fun getIPAddress(): Observable<Int> = Observable.just(mWifiInfo?.ipAddress ?: 0)

    // 得到Mac地址
    fun getMacAddress(): Observable<String> = Observable.just(mWifiInfo?.macAddress ?: "empty mac address")

    // 得到接入的BSSID
    fun getBssid(): Observable<String> = Observable.just(mWifiInfo?.bssid ?: "empty bssid")

    // 得到wifiInfo的所有信息
    fun getWifiInfo(): Observable<WifiInfo> = Observable.just(mWifiInfo)


    // 锁定wifiLock
    fun acquireLock(){
        mWifiLock.acquire()
    }

    // 解锁wifiLock
    fun releaseWifiLock(){
        if (mWifiLock.isHeld){
            mWifiLock.release()
        }
    }

    // 得到配置好的网络wpa_supplicant.conf中的内容，不管有没有配置密码
    fun getConfiguration():Observable<List<WifiConfiguration>> = Observable.just(wifiConfigList)

    // 指定配置好的网络进行连接
    fun connectConfiguration(index: Int): Observable<Boolean>{
        if (index >= wifiConfigList.size){
            return Observable.just(false)
        }
        return Observable.just(mWifiManager.enableNetwork(wifiConfigedSpecifiedList[index].networkId, true))
    }

    private fun createWifiConfig(ssid: String, passWord:String, type: Int): WifiConfiguration{
        val config = WifiConfiguration()
        config.allowedAuthAlgorithms.clear()
        config.allowedGroupCiphers.clear()
        config.allowedKeyManagement.clear()
        config.allowedPairwiseCiphers.clear()
        config.allowedProtocols.clear()
        //指定对应的SSID
        config.SSID = "\"" + ssid + "\""
        val tempConfig = isExist(ssid)
        if (tempConfig != null){
            mWifiManager.removeNetwork(tempConfig.networkId)
        }

        if(type == WIFICIPHER_NOPASS) {
            // 不需要密码的场景
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
        }else if(type == WIFICIPHER_WEP) {
            // 以WEP加密的场景
            config.hiddenSSID = true;
            config.wepKeys[0]= "\"" + passWord + "\""
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
            config.wepTxKeyIndex = 0
        }else if(type == WIFICIPHER_WPA){
            // 以WPA加密的场景，自己测试时，发现热点以WPA2建立时，同样可以用这种配置连接
            config.preSharedKey = "\"" + passWord + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
            config.status = WifiConfiguration.Status.ENABLED
        }
        return config
    }

    private fun isExist(ssid : String): WifiConfiguration?{
        mWifiManager.configuredNetworks.forEach {
            if (it.SSID == "\""+ssid+"\"") {
                return it
            }
        }
        return null
    }

}