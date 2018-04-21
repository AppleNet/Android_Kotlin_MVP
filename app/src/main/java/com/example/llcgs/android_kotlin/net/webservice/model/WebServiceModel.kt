package com.example.llcgs.android_kotlin.net.webservice.model

import com.example.llcgs.android_kotlin.base.rx.RxBus
import com.example.llcgs.android_kotlin.base.rx.RxUtils
import com.example.llcgs.android_kotlin.net.base.BaseNetWorkModel
import com.gomejr.myf.core.kotlin.logD
import com.google.android.gms.internal.ht
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

/**
 * com.example.llcgs.android_kotlin.net.webservice.model.WebServiceModel
 * @author liulongchao
 * @since 2018/4/21
 */
class WebServiceModel: BaseNetWorkModel {

    private val NAME_SPACE = "http://WebXml.com.cn"
    private val URL = "http://www.webxml.com.cn/webservices/weatherwebservices.asmx"
    private val METHOD_NAME = "getWeatherbyCityName"
    private val SOAP_ACTION = "http://WebXml.com.cn/getWeatherbyCityName"

    fun getWeather(cityName: String){
        Observable.just(cityName)
            .map {
                // 新建SoapObject对象
                val rpc = SoapObject(NAME_SPACE, METHOD_NAME)
                // 给SoapObject对象添加属性
                rpc.addProperty("theCityName", cityName)
                // 创建HttpTransportSE对象，并指定WebService的WSDL文档为URL
                val ht = HttpTransportSE(URL)
                // 设置debug模式
                ht.debug = true
                // 获得序列化的envelope
                val envelope = SoapSerializationEnvelope(SoapEnvelope.VER10)
                // 设置bodyOut属性的值为SoapObject对象rpc
                envelope.bodyOut = rpc
                // 指定webservice的类型为doNot
                envelope.dotNet = true
                envelope.setOutputSoapObject(rpc)
                // 使用call方法调用webService方法
                ht.call(SOAP_ACTION, envelope)
                // 获取返回结果
                val result: SoapObject = envelope.bodyIn as SoapObject
                // 使用getResponse方法获得WebService方法的返回结果
                val detail: SoapObject = result.getProperty("getWeatherbyCityNameResult") as SoapObject
                detail.toString().logD()
                // 解析返回数据
                val date = detail.getProperty(6).toString()
                date
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {date ->
                var weatherToday = "今天" + date.split(" ")[0]
                weatherToday = weatherToday + " 天气: " + date.split(" ")[1]
                weatherToday = weatherToday + " 气温: " + date.split(" ")[5]
                weatherToday = weatherToday + " 风力: " + date.split(" ")[7]
                "weatherToday: $weatherToday".logD()
                RxBus.getInstance().post(weatherToday)
            }
     }
}