package com.example.llcgs.android_kotlin.modules.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.list.adapter.MyListAdapter
import com.example.llcgs.android_kotlin.modules.activity.singleInstance.SingleInstanceActivity
import com.example.llcgs.android_kotlin.modules.activity.singleTask.SingleTaskActivity
import com.example.llcgs.android_kotlin.modules.activity.singleTop.SingleTopActivity
import com.example.llcgs.android_kotlin.modules.activity.standard.StandardActivity
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.activity_activity_list.*

/**
 * com.example.llcgs.android_kotlin.modules.activity.ActivityListActivity
 * @author liulongchao
 * @since 2018/3/9
 */
@RouterRule("Activity")
class ActivityListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity_list)
        initRecyclerView()
    }


    private fun initRecyclerView() {
        // 初始化LayoutManager
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayout.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = manager

        val list = resources.getStringArray(R.array.activity_list).toMutableList()
        val adapter = MyListAdapter(list)
        recyclerView.adapter = adapter
        adapter.setOnClickListener(object :MyListAdapter.OnItemClickListener{
            override fun onItemClick(text: Int) {
                when(text){
                    0 ->{
                        startActivity(Intent(this@ActivityListActivity, StandardActivity::class.java))
                    }
                    1 ->{
                        startActivity(Intent(this@ActivityListActivity, SingleTopActivity::class.java))
                    }
                    2 ->{
                        startActivity(Intent(this@ActivityListActivity, SingleTaskActivity::class.java))
                    }
                    3 ->{
                        startActivity(Intent(this@ActivityListActivity, SingleInstanceActivity::class.java))
                    }
                }
            }
        })
    }
}