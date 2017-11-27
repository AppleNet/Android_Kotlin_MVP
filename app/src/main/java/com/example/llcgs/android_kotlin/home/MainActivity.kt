package com.example.llcgs.android_kotlin.home

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.home.adapter.MainAdapter
import com.example.llcgs.android_kotlin.home.bean.User
import com.example.llcgs.android_kotlin.home.presenter.impl.LoginPresneter
import com.example.llcgs.android_kotlin.home.view.LoginView
import com.example.llcgs.android_kotlin.kotlin.basicsyntax.SecondActivity
import com.example.llcgs.android_kotlin.utils.CustomerDecoration
import com.example.llcgs.android_kotlin.utils.SimpleItemDecoration
import com.gomejr.myf.core.kotlin.logD
import com.lzh.nonview.router.Router
import com.lzh.nonview.router.anno.RouterRule
import com.qihoo360.replugin.RePlugin
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

// 因为指定了baseUrl。 所以这里会使用baseUrl做组合。
@RouterRule("main")
class MainActivity : BaseActivity<LoginView, LoginPresneter>(), LoginView, (String) -> Unit {

    private lateinit var adapter:MainAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hello.text = "Welcome to Kotlin!"

        val rxPermission = RxPermissions(this@MainActivity)
        rxPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe {
                    initPlugin()
                }

        initViews()

    }

    private fun initViews(){
        val array = resources.getStringArray(R.array.main_menu).toList()
        adapter = MainAdapter()
        adapter.list = array as ArrayList<String>
        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter
        val manager = GridLayoutManager(this, 3)
        manager.orientation = LinearLayout.VERTICAL
        recyclerView.layoutManager = manager
        recyclerView.setHasFixedSize(true)
        val build = CustomerDecoration.Builder.getInstance()
                .orientation(LinearLayout.HORIZONTAL)
                .top(20)
                .bottom(0)
                .color(ContextCompat.getColor(this, R.color.divider_gray))
                .build()
        recyclerView.addItemDecoration(build)
    }

    override fun invoke(string: String) {
        when(string){
            "登录" ->{
                startTo<SecondActivity>("hello kotlin")
            }
            "插件路由" ->{
                val name = editText.text.toString()
                val pwd = editText2.text.toString()
                val user = User(name, pwd)
                val bundle = Bundle()
                bundle.putString("id", "001")
                bundle.putParcelable("user",user)
                Router.create("host://plugin").activityRoute.addExtras(bundle).open(this)
            }
            "插件列表" ->{
                Router.create("host://listPlugin").activityRoute.open(this)
            }
            "MVVM" ->{
                Router.create("host://mvvm").activityRoute.open(this)
            }
            "Architecture" ->{
                Router.create("host://Architecture").activityRoute.open(this)
            }
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

    private fun initPlugin(){
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
        val path1 = Environment.getExternalStorageDirectory().absolutePath + File.separator + "demo1.apk"
        val file1 = File(path1)
        if (file1.exists()){
            "file1 exists start install".logD()
            val pi = RePlugin.install(path1)
            "pi == null result:  ${pi == null}".logD()
            if (pi != null){
                val preload = RePlugin.preload(pi)
                "load result:  $preload".logD()
                pi.name.logD()
            }
        }
    }

    override fun doLoginSuccess() {
        val name = editText.text.toString()
        val pwd = editText2.text.toString()
        val user = User(name, pwd)
        val bundle = Bundle()
        bundle.putString("id", "id")
        bundle.putParcelable("user",user)
        Router.create("host://list").activityRoute.addExtras(bundle).requestCode(100).open(this)
    }

    override fun doLoginFail() {
    }

    override fun createPresenter(): LoginPresneter = LoginPresneter(this)

    private inline fun <reified T : AppCompatActivity> Activity.startTo(id : String){
        val name = editText.text.toString()
        val pwd = editText2.text.toString()
        val user = User(name, pwd)
        intent.putExtra("id", id)
        intent.putExtra("user", user)
        mPresenter.doLogin(user)
    }

}
