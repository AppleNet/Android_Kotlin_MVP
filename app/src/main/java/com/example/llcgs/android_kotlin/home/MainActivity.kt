package com.example.llcgs.android_kotlin.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.WindowManager
import android.widget.LinearLayout
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.base.rx.MyObserver
import com.example.llcgs.android_kotlin.home.adapter.MainAdapter
import com.example.llcgs.android_kotlin.home.bean.User
import com.example.llcgs.android_kotlin.home.presenter.impl.LoginPresenter
import com.example.llcgs.android_kotlin.home.view.LoginView
import com.example.llcgs.android_kotlin.kotlin.basicsyntax.SecondActivity
import com.example.llcgs.android_kotlin.utils.itemdecoration.CustomerDecoration
import com.example.llcgs.android_kotlin.utils.log.logD
import com.lzh.nonview.router.Router
import com.lzh.nonview.router.anno.RouterRule
import com.qihoo360.replugin.RePlugin
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

// 因为指定了baseUrl。 所以这里会使用baseUrl做组合。
@RouterRule("main")
class MainActivity : BaseActivity<LoginView, LoginPresenter>(), LoginView, (String) -> Unit {

    private lateinit var adapter:MainAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)
        val rxPermission = RxPermissions(this@MainActivity)
        rxPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(object : MyObserver<Boolean>(){
                    override fun onNext(t: Boolean) {
                        if (t){
                            initPlugin()
                        }
                    }
                })
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
            "Kotlin" ->{
                startTo<SecondActivity>("hello kotlin")
            }
            "Router" ->{
                val name = editText.text.toString()
                val pwd = editText2.text.toString()
                val user = User(name, pwd)
                val bundle = Bundle()
                bundle.putString("id", "001")
                bundle.putParcelable("user",user)
                Router.create("host://plugin").activityRoute.addExtras(bundle).open(this)
            }
            "Router List" ->{
                // 聚美路由
                Router.create("host://listPlugin").activityRoute.open(this)
            }
            "MVVM" ->{
                // MVVM
                Router.create("host://mvvm").activityRoute.open(this)
            }
            "Architecture" ->{
                // Google 架构
                Router.create("host://Architecture").activityRoute.open(this)
            }
            "Material Design"->{
                // 材料设计
                Router.create("host://Material_Design").activityRoute.open(this)
            }
            "Rx" ->{
                // 插件形式 接入 Rx 系列 https://github.com/AppleNet/Android_Rx
                RePlugin.startActivity(this, RePlugin.createIntent("Rx", "com.example.llcgs.android_rx.rxbinding.MainActivity"))
            }
            "Design Pattern" ->{
                // 设计模式
                Router.create("host://Design_Pattern").activityRoute.open(this)
            }
            "Material Animation" ->{
                Router.create("host://Material_Animation").activityRoute.open(this)
            }
            "Material MediaPlayer" ->{
                Router.create("host://Material_MediaPlayer").activityRoute.open(this)
            }
            "Activity" ->{
                Router.create("host://Activity").activityRoute.open(this)
            }
            "Service" ->{
                Router.create("host://Service").activityRoute.open(this)
            }
            "Modularization" ->{
                Router.create("plugin://James").open(this)
            }
            "Algorithms" ->{
                Router.create("host://Algorithms").activityRoute.open(this)
            }
            "NetWork" ->{
                Router.create("host://NetWork").activityRoute.open(this)
            }
            "ConstraintLayout" ->{
                Router.create("host://ConstraintLayout").activityRoute.open(this)
            }
            "Animator" ->{
                // @Link AnimatorActivity
                Router.create("host://Animator").activityRoute.open(this)
            }
            "CustomView" ->{
                // @Link CustomViewActivity
                Router.create("host://CustomView").activityRoute.open(this)
            }
            "Tinker" ->{
                // @Link TinkerActivity
                Router.create("host://Tinker").activityRoute.open(this)
            }
            "ViewDispatcher" ->{
                // @Link ViewDispatcherActivity
                Router.create("host://ViewDispatcher").activityRoute.open(this)
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

    override fun createPresenter(): LoginPresenter = LoginPresenter(this)

    private inline fun <reified T : AppCompatActivity> Context.startTo(id : String){
        val name = editText.text.toString()
        val pwd = editText2.text.toString()
        val user = User(name, pwd)
        intent.putExtra("id", id)
        intent.putExtra("user", user)
        mPresenter.doLogin(user)
    }

}
