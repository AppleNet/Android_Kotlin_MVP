package com.example.llcgs.android_kotlin.modules.service

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.list.adapter.MyListAdapter
import com.example.llcgs.android_kotlin.modules.service.bind.BindServiceActivity
import com.example.llcgs.android_kotlin.modules.service.intentservice.IntentServiceActivity
import com.example.llcgs.android_kotlin.modules.service.start.StartServiceActivity
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.activity_service_list.*

/**
 * com.example.llcgs.android_kotlin.modules.service.ServiceListActivity
 * @author liulongchao
 * @since 2018/3/21
 */
@RouterRule("Service")
class ServiceListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_list)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        // 初始化LayoutManager
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayout.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = manager

        val list = resources.getStringArray(R.array.service_list).toMutableList()
        val adapter = MyListAdapter(list)
        recyclerView.adapter = adapter
        adapter.setOnClickListener(object : MyListAdapter.OnItemClickListener{
            override fun onItemClick(text: Int) {
                when(text){
                    0 ->{
                        startActivity(Intent(this@ServiceListActivity, StartServiceActivity::class.java))
                    }
                    1 ->{
                        startActivity(Intent(this@ServiceListActivity, BindServiceActivity::class.java))
                    }
                    2 ->{
                        startActivity(Intent(this@ServiceListActivity, IntentServiceActivity::class.java))
                    }
                }
            }
        })
    }
}