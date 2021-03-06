package com.example.llcgs.android_kotlin.net.xmpp

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.net.xmpp.presenter.IXmppPresenter
import com.example.llcgs.android_kotlin.net.xmpp.presenter.impl.XmppPresenter
import kotlinx.android.synthetic.main.activity_xmpp.*

/**
 * com.example.llcgs.android_kotlin.net.xmpp.XmppActivity
 * @author liulongchao
 * @since 2018/5/4
 */
class XmppActivity : BaseXmppActivity<IXmppPresenter>(), View.OnClickListener {

    private var isLogin = false

    override fun createPresenter(): IXmppPresenter = XmppPresenter(this)

    override fun getLayoutId() = R.layout.activity_xmpp

    override fun initViews() {
        signIn.setOnClickListener(this)
        signUp.setOnClickListener(this)
        add.setOnClickListener(this)
    }

    override fun initData() {
        super.initData()
        userName.setText(intent.getStringExtra("userName"))
        password.setText(intent.getStringExtra("userPwd"))
    }

    override fun onClick(v: View) {
        val userName = userName.text.toString()
        val userPwd = password.text.toString()
        val email = email.text.toString()
        val alias = alias.text.toString()
        when (v.id) {
            R.id.signIn -> {
                // 登录
                if (!userPwdLayout.isShown){
                    userPwdLayout.visibility = View.VISIBLE
                    return
                }
                if (userName.isEmpty() || userPwd.isEmpty()) {
                    Toast.makeText(this, "please input name or pwd", Toast.LENGTH_LONG).show()
                    return
                }
                mPresenter.login(userName, userPwd)
            }
            R.id.signUp -> {
                // 注册
                startActivity(Intent(this@XmppActivity, XmppRegisterActivity::class.java))
            }
            R.id.add ->{
                if(!TextUtils.isEmpty(userName) && isLogin){
                    startActivity(Intent(this@XmppActivity, XmppAddActivity::class.java))
                }else{
                    //
                    Toast.makeText(this, "未登录,请先登录!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onLoginSuccess(boolean: Boolean) {
        isLogin = boolean
        if (boolean){
            Toast.makeText(this, "login success", Toast.LENGTH_LONG).show()
            startActivity(Intent(this@XmppActivity, XmppListActivity::class.java))
        }
    }

    override fun onDestroy() {
        mPresenter.logOut()
        super.onDestroy()
    }
}