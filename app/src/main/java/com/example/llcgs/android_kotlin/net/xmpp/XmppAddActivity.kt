package com.example.llcgs.android_kotlin.net.xmpp

import android.content.Intent
import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.net.xmpp.presenter.IXmppPresenter
import com.example.llcgs.android_kotlin.net.xmpp.presenter.impl.XmppPresenter
import com.gomejr.myf.core.kotlin.logD
import kotlinx.android.synthetic.main.activity_xmpp_add.*

/**
 * com.example.llcgs.android_kotlin.net.xmpp.XmppAddActivity
 * @author liulongchao
 * @since 2018/5/7
 */
class XmppAddActivity: BaseXmppActivity<IXmppPresenter>(), View.OnClickListener {


    override fun createPresenter(): IXmppPresenter = XmppPresenter(this)

    override fun getLayoutId()= R.layout.activity_xmpp_add

    override fun initViews() {
        addCount.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val userName = userName.text.toString()
        when(v.id){
            R.id.addCount ->{
                mPresenter.addFriend(userName)
            }
        }
    }

    override fun onAddSuccess(boolean: Boolean) {
        "add result: $boolean".logD()
        startActivity(Intent(this@XmppAddActivity, XmppListActivity::class.java))
    }

}