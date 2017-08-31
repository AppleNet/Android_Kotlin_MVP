package com.example.llcgs.android_kotlin.pluginlist

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.Toast
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.plugin.adapter.PluginAdapter
import com.example.llcgs.android_kotlin.pluginlist.presenter.impl.PluginListPresenter
import com.example.llcgs.android_kotlin.pluginlist.view.PluginListView
import com.gomejr.myf.core.kotlin.logD
import com.lzh.nonview.router.anno.RouterRule
import com.qihoo360.replugin.RePlugin
import kotlinx.android.synthetic.main.activity_plugin_list.*




@RouterRule("listPlugin")
class PluginListActivity : BaseActivity<PluginListView, PluginListPresenter>(), PluginListView {

    override fun createPresenter()= PluginListPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin_list)
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
                0 ->{
                    if (RePlugin.isPluginInstalled("plugin")){
                        // 刻意以“Alias（别名）”来打开
                        RePlugin.startActivity(this@PluginListActivity, RePlugin.createIntent("plugin", "com.llc.android_lcplugin.PluginMainActivity"))
                    }else{
                        // 未安装
                        Toast.makeText(this, "is not installed", Toast.LENGTH_SHORT).show()
                    }
                }
                1 ->{
                    // 刻意以包名打开
                    if (!RePlugin.isPluginRunning("plugin")){
                        RePlugin.startActivity(this@PluginListActivity, RePlugin.createIntent("com.llc.android_lcplugin", "com.llc.android_lcplugin.PluginMainActivity"))
                    }else{
                        // 正在运行
                        Toast.makeText(this, "is running!", Toast.LENGTH_SHORT).show()
                    }
                }
                2 ->{
                    // 打开外置插件
                    RePlugin.startActivity(this@PluginListActivity, RePlugin.createIntent("demo1", "com.qihoo360.replugin.sample.demo1.MainActivity"))
                }
                3 ->{
                    // TODO 目前官方demo有问题
                    startActivity(Intent(this@PluginListActivity, PluginListFragmentActivity::class.java))
                }
            }
        }
    }

}
