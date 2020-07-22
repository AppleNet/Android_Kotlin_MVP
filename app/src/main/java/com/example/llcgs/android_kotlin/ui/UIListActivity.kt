package com.example.llcgs.android_kotlin.ui;

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.list.adapter.MyListAdapter
import com.example.llcgs.android_kotlin.ui.recyclerview.RecyclerViewActivity
import com.example.llcgs.android_kotlin.ui.viewpager.ViewPagerActivity
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.activity_activity_list.*

/**
 * com.example.llcgs.android_kotlin.ui.UIListActivity
 * @author liulongchao
 * @since 2018/12/25
 */

@RouterRule("ViewPager_Activity_TabLayout")
class UIListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_algorithms_list)
        initRecyclerView()
    }


    private fun initRecyclerView() {
        // 初始化LayoutManager
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayout.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = manager

        val list = resources.getStringArray(R.array.ui_list).toMutableList()
        val adapter = MyListAdapter(list)
        recyclerView.adapter = adapter
        adapter.setOnClickListener(object : MyListAdapter.OnItemClickListener{
            override fun onItemClick(text: Int) {
                when(text){
                    0 ->{
                        startActivity(Intent(this@UIListActivity, ViewPagerActivity::class.java))
                    }
                    1 ->{

                    }
                    2 ->{
                        startActivity(Intent(this@UIListActivity, RecyclerViewActivity::class.java))
                    }
                    3 ->{
                    }
                    4 ->{
                    }
                    5 ->{
                    }
                    6 ->{
                    }
                    7 ->{
                    }
                }
            }
        })
    }

}
