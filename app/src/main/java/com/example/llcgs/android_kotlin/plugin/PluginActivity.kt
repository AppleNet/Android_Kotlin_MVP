package com.example.llcgs.android_kotlin.plugin

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.home.bean.User
import com.example.llcgs.android_kotlin.plugin.adapter.PluginAdapter
import com.example.llcgs.android_kotlin.plugin.interceptor.PluginInterceptor
import com.example.llcgs.android_kotlin.plugin.presenter.impl.PluginPresenter
import com.example.llcgs.android_kotlin.plugin.view.PluginView
import com.gomejr.myf.core.kotlin.logD
import com.lzh.nonview.router.Router
import com.lzh.nonview.router.anno.RouterRule
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
                    Router.create("plugin://main").activityRoute.addExtras(bundle).open(this)
                }
                2 ->{
                    // 只添加拦截器
                    Router.create("plugin://main").activityRoute.addInterceptor(PluginInterceptor(this.id?:"", this.user?:User("",""))).open(this)
                }
            }
        }
    }
}
