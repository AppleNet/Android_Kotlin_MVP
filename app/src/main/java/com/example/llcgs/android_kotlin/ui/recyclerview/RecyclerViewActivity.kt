package com.example.llcgs.android_kotlin.ui.recyclerview;

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.ui.recyclerview.adapter.RecyclerViewAdapter
import com.example.llcgs.android_kotlin.ui.recyclerview.bean.Start
import com.example.llcgs.android_kotlin.ui.recyclerview.itemdecoration.NBAItemDecoration
import com.example.llcgs.android_kotlin.ui.recyclerview.presenter.impl.RecyclerViewPresenter
import com.example.llcgs.android_kotlin.ui.recyclerview.view.RecyclerViewView
import kotlinx.android.synthetic.main.activity_recyclerview.*

class RecyclerViewActivity: BaseActivity<RecyclerViewView, RecyclerViewPresenter>() {

    val starList = ArrayList<Start>()

    override fun createPresenter()= RecyclerViewPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        init()
        // setLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)
        // 自定义分割线
        recyclerView.addItemDecoration(NBAItemDecoration(this))
        // setAdapter
        recyclerView.adapter = RecyclerViewAdapter(this, starList)

    }

    private fun init() {
        for (i in 0..4) {
            for (j in 0..20) {
                if(i % 2 == 0) {
                    starList.add(Start("科比 $j", "NBA $i"))
                } else {
                    starList.add(Start("姚明 $j", "CBA $i"))
                }
            }
        }
    }
}
