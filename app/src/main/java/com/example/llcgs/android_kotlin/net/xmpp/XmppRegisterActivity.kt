package com.example.llcgs.android_kotlin.net.xmpp

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.net.xmpp.presenter.IXmppPresenter
import com.example.llcgs.android_kotlin.net.xmpp.presenter.impl.XmppPresenter
import com.example.llcgs.android_kotlin.net.xmpp.view.XmppView
import com.example.llcgs.android_kotlin.utils.log.logD
import kotlinx.android.synthetic.main.activity_xmpp_register.*

/**
 * com.example.llcgs.android_kotlin.net.xmpp.XmppRegisterActivity
 * @author liulongchao
 * @since 2018/5/7
 */
class XmppRegisterActivity: BaseXmppActivity<IXmppPresenter>(), XmppView, View.OnClickListener {

    override fun createPresenter(): IXmppPresenter = XmppPresenter(this)

    override fun getLayoutId()= R.layout.activity_xmpp_register

    override fun initViews() {
        register.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val userName = userName.text.toString()
        val userPwd = password.text.toString()
        val email = email.text.toString()
        val alias = alias.text.toString()
        when(v.id){
            R.id.register ->{
                if (userName.isEmpty() || userPwd.isEmpty() || email.isEmpty() || alias.isEmpty()) {
                    Toast.makeText(this, "please input name or pwd", Toast.LENGTH_LONG).show()
                    return
                }
                mPresenter.registerUser(userName, userPwd, email, alias)
            }
        }
    }

    override fun onRegisterSuccess(boolean: Boolean) {
        "register result: $boolean".logD()
        if (boolean){
            Toast.makeText(this, "register success", Toast.LENGTH_LONG).show()
            startActivity(Intent(this@XmppRegisterActivity, XmppActivity::class.java).apply {
                putExtra("userName", userName.text.toString())
                putExtra("userPwd", password.text.toString())
            })
            finish()
        }
    }

}