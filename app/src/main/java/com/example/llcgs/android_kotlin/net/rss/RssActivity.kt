package com.example.llcgs.android_kotlin.net.rss

import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.net.base.BaseNetWorkActivity
import com.example.llcgs.android_kotlin.net.rss.adapter.RssAdapter
import com.example.llcgs.android_kotlin.net.rss.adapter.RssRetrofitAdapter
import com.example.llcgs.android_kotlin.net.rss.bean.retrofit.XmlRss
import com.example.llcgs.android_kotlin.net.rss.bean.xml.Rss
import com.example.llcgs.android_kotlin.net.rss.presenter.IRssPresenter
import com.example.llcgs.android_kotlin.net.rss.presenter.impl.RssPresenter
import com.example.llcgs.android_kotlin.net.rss.view.RssView
import com.example.llcgs.android_kotlin.utils.log.logD
import kotlinx.android.synthetic.main.activity_rss.*

/**
 * com.example.llcgs.android_kotlin.net.rss.RssActivity
 * @author liulongchao
 * @since 2018/4/23
 */
class RssActivity: BaseNetWorkActivity<IRssPresenter>(), RssView {

    private lateinit var adapter: RssAdapter
    private lateinit var retrofitAdapter: RssRetrofitAdapter

    override fun createPresenter(): IRssPresenter= RssPresenter(this)

    override fun getLayoutId()= R.layout.activity_rss

    override fun initViews() {
        adapter = RssAdapter()
        retrofitAdapter = RssRetrofitAdapter()
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayout.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = manager
    }

    override fun onGetXmlRss(xmlRss: ArrayList<Rss>) {
        adapter.setNewData(xmlRss)
        recyclerView.adapter = adapter
    }

    override fun onGetXmlRssByRetrofit(xmlRss: XmlRss) {
        xmlRss.channel.title.logD()
        xmlRss.channel.itemList.size.logD()
        retrofitAdapter.setNewData(xmlRss.channel.itemList)
        recyclerView.adapter = retrofitAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_rss, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.xml ->{
                mPresenter.getRssContent()
                return true
            }
            R.id.retrofit ->{
                mPresenter.getRssContentByRetrofit()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}