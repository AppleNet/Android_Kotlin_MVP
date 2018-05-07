package com.example.llcgs.android_kotlin.net.xmpp

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.net.xmpp.adapter.XmppListAdapter
import com.example.llcgs.android_kotlin.net.xmpp.presenter.IXmppPresenter
import com.example.llcgs.android_kotlin.net.xmpp.presenter.impl.XmppPresenter
import com.example.llcgs.android_kotlin.net.xmpp.view.XmppView
import kotlinx.android.synthetic.main.activity_xmpp_list.*
import org.jivesoftware.smack.RosterEntry

/**
 * com.example.llcgs.android_kotlin.net.xmpp.XmppListActivity
 * @author liulongchao
 * @since 2018/5/7
 */
class XmppListActivity: BaseXmppActivity<IXmppPresenter>(), XmppView {

    private lateinit var adapter: XmppListAdapter

    override fun createPresenter(): IXmppPresenter= XmppPresenter(this)

    override fun getLayoutId()= R.layout.activity_xmpp_list

    override fun initViews() {
        adapter = XmppListAdapter()
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayout.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
        mPresenter.getFriendList()
    }

    override fun onGetFriendList(collection: Collection<RosterEntry>) {
        adapter.data.clear()
        adapter.setNewData(collection.toMutableList())
        adapter.notifyDataSetChanged()
    }
}