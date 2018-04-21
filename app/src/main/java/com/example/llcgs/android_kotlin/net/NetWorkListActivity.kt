package com.example.llcgs.android_kotlin.net

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.algorithms.bubble.BubbleSortActivity
import com.example.llcgs.android_kotlin.algorithms.cardinality.CardinalitySortActivity
import com.example.llcgs.android_kotlin.algorithms.fast.FastSortActivity
import com.example.llcgs.android_kotlin.algorithms.heap.HeapSortActivity
import com.example.llcgs.android_kotlin.algorithms.insert.InsertSortActivity
import com.example.llcgs.android_kotlin.algorithms.merge.MergeSortActivity
import com.example.llcgs.android_kotlin.algorithms.select.SelectSortActivity
import com.example.llcgs.android_kotlin.algorithms.shell.ShellSortActivity
import com.example.llcgs.android_kotlin.list.adapter.MyListAdapter
import com.example.llcgs.android_kotlin.modules.activity.singleInstance.SingleInstanceActivity
import com.example.llcgs.android_kotlin.modules.activity.singleTask.SingleTaskActivity
import com.example.llcgs.android_kotlin.modules.activity.singleTop.SingleTopActivity
import com.example.llcgs.android_kotlin.net.socket_tcp.SocketTCPActivity
import com.example.llcgs.android_kotlin.net.webservice.WebServiceActivity
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.activity_activity_list.*

/**
 * com.example.llcgs.android_kotlin.algorithms.AlgorithmsListActivity
 * @author liulongchao
 * @since 2018/3/29
 */
@RouterRule("NetWork")
class NetWorkListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_list)
        initRecyclerView()
    }


    private fun initRecyclerView() {
        // 初始化LayoutManager
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayout.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = manager

        val list = resources.getStringArray(R.array.network_list).toMutableList()
        val adapter = MyListAdapter(list)
        recyclerView.adapter = adapter
        adapter.setOnClickListener(object :MyListAdapter.OnItemClickListener{
            override fun onItemClick(text: Int) {
                when(text){
                    0 ->{
                        startActivity(Intent(this@NetWorkListActivity, SocketTCPActivity::class.java))
                    }
                    1 ->{
                        //startActivity(Intent(this@NetWorkListActivity, InsertSortActivity::class.java))
                    }
                    2 ->{
                        startActivity(Intent(this@NetWorkListActivity, WebServiceActivity::class.java))
                    }
                    3 ->{
                        startActivity(Intent(this@NetWorkListActivity, SelectSortActivity::class.java))
                    }
                    4 ->{
                        startActivity(Intent(this@NetWorkListActivity, HeapSortActivity::class.java))
                    }
                    5 ->{
                        startActivity(Intent(this@NetWorkListActivity, FastSortActivity::class.java))
                    }
                    6 ->{
                        startActivity(Intent(this@NetWorkListActivity, MergeSortActivity::class.java))
                    }
                    7 ->{
                        startActivity(Intent(this@NetWorkListActivity, CardinalitySortActivity::class.java))
                    }
                }
            }
        })
    }
}