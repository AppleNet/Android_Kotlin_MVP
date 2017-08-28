package com.example.llcgs.android_kotlin.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.basicsyntax.SecondActivity
import com.example.llcgs.android_kotlin.home.bean.User
import com.example.llcgs.android_kotlin.home.presenter.impl.LoginPresneter
import com.example.llcgs.android_kotlin.home.view.LoginView
import com.example.llcgs.android_kotlin.list.ListActivity
import com.gomejr.myf.core.kotlin.logD
import com.lzh.nonview.router.Router
import com.lzh.nonview.router.anno.RouterRule
import com.qihoo360.replugin.RePlugin
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

// 因为指定了baseUrl。 所以这里会使用baseUrl做组合。
@RouterRule("main")
class MainActivity : BaseActivity<LoginView, LoginPresneter>(), LoginView {


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hello.text = "Welcome to Kotlin!"
        button.setOnClickListener {
            startTo<SecondActivity>("hello kotlin")
        }

        val rxPermission = RxPermissions(this@MainActivity)
        rxPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe {
                    initPlugin()
                }
        button30.setOnClickListener{
            Router.create("plugin://main").open(this)
        }
        button31.setOnClickListener {
            RePlugin.startActivity(this@MainActivity, RePlugin.createIntent("plugin", "com.llc.android_lcplugin.PluginMainActivity"))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1){
            val grantResult = grantResults[0]
            val granted = grantResult == PackageManager.PERMISSION_GRANTED
            if (granted){
                initPlugin()
            }
        }
    }

    fun initPlugin(){
        val path = Environment.getExternalStorageDirectory().absolutePath + File.separator + "plugin.apk"
        val file = File(path)
        if (file.exists()){
            "file exists start install".logD()
            val pi = RePlugin.install(path)
            "pi == null result:  ${pi == null}".logD()
            if (pi != null){
                val preload = RePlugin.preload(pi)
                "load result:  ${preload}".logD()
                pi.name.logD()
            }
        }
    }

    override fun doLoginSuccess() {
        val intent = Intent(this, ListActivity::class.java)
        val name = editText.text.toString()
        val pwd = editText2.text.toString()
        val user = User(name, pwd)
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
        val name = editText.text.toString()
        val pwd = editText2.text.toString()
        val user = User(name, pwd)
        intent.putExtra("id", id)
        intent.putExtra("user", user)
        mPresenter.doLogin(user)
    }


    override fun onDestroy() {
        //RePlugin.uninstall(Environment.getExternalStorageDirectory().absolutePath + File.separator + "android_lcplugin.apk")
        super.onDestroy()
    }
}
