package com.example.llcgs.android_kotlin.net.rss.model

import com.example.llcgs.android_kotlin.base.network.RetrofitHelper
import com.example.llcgs.android_kotlin.net.base.BaseNetWorkModel
import com.example.llcgs.android_kotlin.net.rss.bean.retrofit.XmlRss
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * com.example.llcgs.android_kotlin.net.rss.model.RssModel
 * @author liulongchao
 * @since 2018/4/23
 */
class RssModel: BaseNetWorkModel {

    fun getRssContent(): Observable<String>{
        var xmlSourceStr = ""
        return Observable.just(xmlSourceStr)
            .map {
                val url = URL("http://www.sciencenet.cn/xml/news-0.aspx?news=0")
                val urlConnection = url.openConnection() as HttpURLConnection
                val isReader = InputStreamReader(urlConnection.inputStream, "UTF-8")
                val br = BufferedReader(isReader)
                val sb = StringBuffer()
                var line: String? = ""
                while (line != null){
                    sb.append(line)
                    line = br.readLine()
                }
                isReader.close()
                urlConnection.disconnect()
                xmlSourceStr = sb.toString()
                xmlSourceStr
            }
            .subscribeOn(Schedulers.io())
    }

    fun getRssContentByRetrofit(): Observable<XmlRss> =
        RetrofitHelper.getService("http://www.sciencenet.cn/").getRssContent()
}