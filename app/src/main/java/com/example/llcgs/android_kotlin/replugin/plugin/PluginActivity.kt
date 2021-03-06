package com.example.llcgs.android_kotlin.replugin.plugin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.Toast
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.home.bean.User
import com.example.llcgs.android_kotlin.replugin.plugin.adapter.PluginAdapter
import com.example.llcgs.android_kotlin.replugin.plugin.interceptor.PluginInterceptor
import com.example.llcgs.android_kotlin.replugin.plugin.presenter.impl.PluginPresenter
import com.example.llcgs.android_kotlin.replugin.plugin.view.PluginView
import com.example.llcgs.android_kotlin.utils.log.logD
import com.lzh.nonview.router.Router
import com.lzh.nonview.router.anno.RouterRule
import com.lzh.nonview.router.exception.NotFoundException
import com.lzh.nonview.router.launcher.Launcher
import com.lzh.nonview.router.module.RouteRule
import com.lzh.nonview.router.route.RouteCallback
import kotlinx.android.synthetic.main.activity_plugin.*

@RouterRule("plugin")
class PluginActivity : BaseActivity<PluginView, PluginPresenter>(), PluginView {

    private var id:String? = null
    private var user: User? = null

    override fun createPresenter()= PluginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin)
        val intent = intent
        if (intent != null){
            val bundle = intent.extras
            if (bundle != null){
                id = bundle.getString("id")
                user = bundle.getParcelable("user")
            }
        }
        initRecyclerView()
        mPresenter.getListData()
    }

    private fun initRecyclerView() {
        // 初始化LayoutManager
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayout.VERTICAL
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = manager
    }

    override fun onGetListSuccess(list: List<String>) {
        list.size.logD()
        val adapter = PluginAdapter()
        adapter.addData(list)
        recyclerview.adapter = adapter
        adapter.setOnItemClickListener { _, _, position ->
            when(position){
                0->{
                    // 基本启动方式
                    Router.create("plugin://main").open(this)
                }
                1->{
                    // 只添加额外数据
                    val bundle = Bundle()
                    bundle.putString("name", "只添加额外数据")
                    bundle.putString("age", "7")
                    Router.create("plugin://main").baseRoute.addExtras(bundle).open(this)
                }
                2 ->{
                    // 只添加拦截器
                    // TODO 验证拦截器是否生效 将登录页的用户名密码 修改成McGradys 1234567就可以验证了
                    Router.create("plugin://main").baseRoute.addInterceptor(PluginInterceptor(this.id?:"", this.user?:User("",""))).open(this)
                }
                3 ->{
                    // 触发页面路由事件
                    // getActivityRoute() 获取页面路由 来触发相关事件
                    Router.create("plugin1://main").activityRoute.open(this)
                }
                4 ->{
                    // 触发页面路由事件
                    Router.create("plugin2://main").activityRoute.open(this)
                }
                5 ->{
                    // 单次回调监听
                    // 设置单次回调监听器，若不设置。则使用全局的监听器
                    // 为了验证回调 这里使用一个错误的路由地址
                    Router.create("plugin://mains").setCallback(object : RouteCallback{
                        override fun notFound(uri: Uri, e: NotFoundException) {
                            "Uri: ${uri} notFound".logD()
                            Toast.makeText(this@PluginActivity, "Uri: ${uri} notFound", Toast.LENGTH_SHORT).show()
                        }

                        override fun onOpenSuccess(uri: Uri, rule: RouteRule<out RouteRule<*, *>, out Launcher<*>>) {
                            "Uri: ${uri} onOpenSuccess".logD()
                        }

                        override fun onOpenFailed(uri: Uri, e: Throwable) {
                            "Uri: ${uri} onOpenFailed".logD()
                        }
                    }).open(this)
                }
                6 ->{
                    // 启动 WebView
                    Router.create("https://www.baidu.com").open(this)
                }
                7 ->{
                    // 设置请求码 需要在宿主或者其他页面 接收onActivityResult() 回调
                    Router.create("plugin://main").activityRoute.requestCode(100).open(this)
                }
                8 ->{
                    // 设置转场动画
                    Router.create("plugin://main").activityRoute.setAnim(R.anim.anim_fade_in, R.anim.anim_fade_out).open(this)
                }
                9 ->{
                    // 设置Intent Flags
                    Router.create("plugin://main").activityRoute.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).open(this)
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 100){
            val name = data?.getStringExtra("name")
            name?.logD()
            val pwd = data?.getStringExtra("pwd")
            pwd?.logD()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
