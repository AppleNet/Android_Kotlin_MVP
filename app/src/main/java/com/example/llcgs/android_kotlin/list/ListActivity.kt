package com.example.llcgs.android_kotlin.list

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.basicsyntax.SecondActivity
import com.example.llcgs.android_kotlin.classandobject.`object`.FifteenActivity
import com.example.llcgs.android_kotlin.classandobject.classandextends.FiveActivity
import com.example.llcgs.android_kotlin.classandobject.dataclass.TenActivity
import com.example.llcgs.android_kotlin.classandobject.delegation.SixteenActivity
import com.example.llcgs.android_kotlin.classandobject.enumclass.FourteenActivity
import com.example.llcgs.android_kotlin.classandobject.extensions.NineActivity
import com.example.llcgs.android_kotlin.classandobject.genericity.TwelveActivity
import com.example.llcgs.android_kotlin.classandobject.interfaces.SevenActivity
import com.example.llcgs.android_kotlin.classandobject.modifier.EightActivity
import com.example.llcgs.android_kotlin.classandobject.nestclass.ThirteenActivity
import com.example.llcgs.android_kotlin.classandobject.propertyandfield.SixActivity
import com.example.llcgs.android_kotlin.classandobject.propertydelegate.SeventeenActivity
import com.example.llcgs.android_kotlin.classandobject.sealedclass.ElevenActivity
import com.example.llcgs.android_kotlin.codestandards.FourActivity
import com.example.llcgs.android_kotlin.functionandlambda.function.EighteenActivity
import com.example.llcgs.android_kotlin.functionandlambda.inline.TwentyActivity
import com.example.llcgs.android_kotlin.functionandlambda.lambda.NineteenActivity
import com.example.llcgs.android_kotlin.home.bean.User
import com.example.llcgs.android_kotlin.idiom.ThirdActivity
import com.example.llcgs.android_kotlin.list.adapter.MyListAdapter
import com.example.llcgs.android_kotlin.list.presenter.impl.ListPresenter
import com.example.llcgs.android_kotlin.list.view.ListView
import com.example.llcgs.android_kotlin.other.airsafety.TwentyEightActivity
import com.example.llcgs.android_kotlin.other.annotation.ThirtyActivity
import com.example.llcgs.android_kotlin.other.equals.TwentySixActivity
import com.example.llcgs.android_kotlin.other.exception.TwentyNineActivity
import com.example.llcgs.android_kotlin.other.gather.TwentyTwoActivity
import com.example.llcgs.android_kotlin.other.interval.TwentyThreeActivity
import com.example.llcgs.android_kotlin.other.operator.TwentySevenActivity
import com.example.llcgs.android_kotlin.other.reflect.ThirtyOneActivity
import com.example.llcgs.android_kotlin.other.structdeclarations.TwentyOneActivity
import com.example.llcgs.android_kotlin.other.thisexperssion.TwentyFiveActivity
import com.example.llcgs.android_kotlin.other.typecheckchange.TwentyFourActivity
import com.gomejr.myf.core.kotlin.logD
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : BaseActivity<ListView, ListPresenter>(), ListView {

    override fun createPresenter()= ListPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        initRecyclerView()
        // 获取数据
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
        val listAdapter = MyListAdapter(list)
        recyclerview.adapter = listAdapter
        listAdapter.setOnClickListener(object : MyListAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                position.logD()
                when(position){
                    0 ->{
                        startActivity(Intent(this@ListActivity, SecondActivity::class.java).apply {
                            putExtra("id", "id")
                            putExtra("user", User("McGrady", "123456"))
                        })
                    }
                    1 ->{
                        startActivity(Intent(this@ListActivity, ThirdActivity::class.java))
                    }
                    2 ->{
                        startActivity(Intent(this@ListActivity, FourActivity::class.java))
                    }
                    3 ->{
                        startActivity(Intent(this@ListActivity, FiveActivity::class.java))
                    }
                    4 ->{
                        startActivity(Intent(this@ListActivity, SixActivity::class.java))
                    }
                    5 ->{
                        startActivity(Intent(this@ListActivity, SevenActivity::class.java))
                    }
                    6 ->{
                        startActivity(Intent(this@ListActivity, EightActivity::class.java))
                    }
                    7 ->{
                        startActivity(Intent(this@ListActivity, NineActivity::class.java))
                    }
                    8 ->{
                        startActivity(Intent(this@ListActivity, TenActivity::class.java))
                    }
                    9 ->{
                        startActivity(Intent(this@ListActivity, ElevenActivity::class.java))
                    }
                    10 ->{
                        startActivity(Intent(this@ListActivity, TwelveActivity::class.java))
                    }
                    11 ->{
                        startActivity(Intent(this@ListActivity, ThirteenActivity::class.java))
                    }
                    12 ->{
                        startActivity(Intent(this@ListActivity, FourteenActivity::class.java))
                    }
                    13 ->{
                        startActivity(Intent(this@ListActivity, FifteenActivity::class.java))
                    }
                    14 ->{
                        startActivity(Intent(this@ListActivity, SixteenActivity::class.java))
                    }
                    15 ->{
                        startActivity(Intent(this@ListActivity, SeventeenActivity::class.java))
                    }
                    16 ->{
                        startActivity(Intent(this@ListActivity, EighteenActivity::class.java))
                    }
                    17 ->{
                        startActivity(Intent(this@ListActivity, NineteenActivity::class.java))
                    }
                    18 ->{
                        startActivity(Intent(this@ListActivity, TwentyActivity::class.java))
                    }
                    19 ->{
                        startActivity(Intent(this@ListActivity, TwentyOneActivity::class.java))
                    }
                    20 ->{
                        startActivity(Intent(this@ListActivity, TwentyTwoActivity::class.java))
                    }
                    21 ->{
                        startActivity(Intent(this@ListActivity, TwentyThreeActivity::class.java))
                    }
                    22 ->{
                        startActivity(Intent(this@ListActivity, TwentyFourActivity::class.java))
                    }
                    23 ->{
                        startActivity(Intent(this@ListActivity, TwentyFiveActivity::class.java))
                    }
                    24 ->{
                        startActivity(Intent(this@ListActivity, TwentySixActivity::class.java))
                    }
                    25 ->{
                        startActivity(Intent(this@ListActivity, TwentySevenActivity::class.java))
                    }
                    26 ->{
                        startActivity(Intent(this@ListActivity, TwentyEightActivity::class.java))
                    }
                    27 ->{
                        startActivity(Intent(this@ListActivity, TwentyNineActivity::class.java))
                    }
                    28 ->{
                        startActivity(Intent(this@ListActivity, ThirtyActivity::class.java))
                    }
                    29 ->{
                        startActivity(Intent(this@ListActivity, ThirtyOneActivity::class.java))
                    }
                }
            }
        })
    }
}
