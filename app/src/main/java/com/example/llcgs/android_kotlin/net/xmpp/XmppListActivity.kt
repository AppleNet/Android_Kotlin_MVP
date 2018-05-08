package com.example.llcgs.android_kotlin.net.xmpp

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
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
class XmppListActivity: BaseXmppActivity<IXmppPresenter>(), XmppView, View.OnClickListener {

    private lateinit var adapter: XmppListAdapter
    private lateinit var emptyView: View
    private lateinit var holder: ViewHolder


    override fun createPresenter(): IXmppPresenter= XmppPresenter(this)

    override fun getLayoutId()= R.layout.activity_xmpp_list

    override fun initViews() {
        adapter = XmppListAdapter()

        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayout.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = manager

        emptyView = LayoutInflater.from(this).inflate(R.layout.view_item_empty_ry, recyclerView, false)
        holder = ViewHolder(emptyView)
        holder.button.setOnClickListener(this)
        adapter.emptyView = emptyView

        recyclerView.adapter = adapter

        mPresenter.getFriendList()
    }

    override fun onGetFriendList(collection: Collection<RosterEntry>) {
        adapter.data.clear()
        adapter.setNewData(collection.toMutableList())
        adapter.notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.add ->{
                startActivity(Intent(this@XmppListActivity, XmppAddActivity::class.java))
            }
        }
    }

    class ViewHolder(var view: View){
        val button: Button = view.findViewById(R.id.add)
    }
}