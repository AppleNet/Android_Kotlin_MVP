package com.example.llcgs.android_kotlin.net.rss.presenter.impl

import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.net.rss.bean.xml.Rss
import com.example.llcgs.android_kotlin.net.rss.model.RssModel
import com.example.llcgs.android_kotlin.net.rss.presenter.IRssPresenter
import com.example.llcgs.android_kotlin.net.rss.view.RssView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.StringReader

/**
 * com.example.llcgs.android_kotlin.net.rss.presenter.impl.RssPresenter
 * @author liulongchao
 * @since 2018/4/23
 */
class RssPresenter(private val view: RssView): IRssPresenter {

    private val model = RssModel()

    override fun getRssContent() {
        model.getRssContent()
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .map {
                val factory = XmlPullParserFactory.newInstance()
                val parser = factory.newPullParser()
                parser.setInput(BufferedReader(StringReader(it)))
                var text = ""
                var rss = Rss()
                val list: ArrayList<Rss> = ArrayList()
                var eventType = parser.eventType
                while (eventType != XmlPullParser.END_DOCUMENT){
                    if (eventType == XmlPullParser.START_TAG) {
                        if ("item" == parser.name){
                            rss = Rss()
                        }
                    }else if(eventType == XmlPullParser.END_TAG){
                        when {
                            "item" == parser.name ->{ list.add(rss) }
                            "title" == parser.name -> rss.item.title = text
                            "link" == parser.name -> rss.item.link = text
                            "description" == parser.name -> rss.item.guid = text
                            "copyright" == parser.name -> rss.item.copyright = text
                            "pubDate" == parser.name -> rss.item.pubDate = text
                            "comments" == parser.name -> rss.item.comments = text
                        }
                    }else if (eventType == XmlPullParser.TEXT){
                        text = parser.text.toString()
                    }
                    eventType = parser.next()
                }
                list
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onGetXmlRss(it)
            }
    }

    override fun getRssContentByRetrofit() {
        model.getRssContentByRetrofit()
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onGetXmlRssByRetrofit(it)
            }
    }
}