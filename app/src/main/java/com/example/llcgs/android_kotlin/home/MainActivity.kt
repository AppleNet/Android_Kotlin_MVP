package com.example.llcgs.android_kotlin.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.basicsyntax.SecondActivity
import com.example.llcgs.android_kotlin.home.bean.User
import com.example.llcgs.android_kotlin.home.presenter.impl.LoginPresneter
import com.example.llcgs.android_kotlin.home.view.LoginView
import com.example.llcgs.android_kotlin.list.ListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<LoginView, LoginPresneter>(), LoginView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hello.setText("welcome kotlin")
        button.setOnClickListener {
            startTo<SecondActivity>("hello kotlin")
        }
    }

    override fun doLoginSuccess() {
        var intent = Intent(this, ListActivity::class.java)
        var name = editText.text.toString()
        var pwd = editText2.text.toString()
        var user = User(name, pwd)
        intent.putExtra("id", "id")
        intent.putExtra("user", user)
        startActivity(intent)
    }

    override fun doLoginFail() {
    }

    override fun createPresenter(): LoginPresneter {
        return LoginPresneter(this)
    }

    inline fun <reified T : AppCompatActivity> Activity.startTo(id : String){
        var name = editText.text.toString()
        var pwd = editText2.text.toString()
        var user = User(name, pwd)
        intent.putExtra("id", id)
        intent.putExtra("user", user)
        mPresenter.doLogin(user)
    }

}
