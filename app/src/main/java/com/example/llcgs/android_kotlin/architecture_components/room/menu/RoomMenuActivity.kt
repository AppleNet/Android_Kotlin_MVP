package com.example.llcgs.android_kotlin.architecture_components.room.menu

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.base.BaseOwnerActivity
import com.example.llcgs.android_kotlin.architecture_components.room.demo.RoomActivity
import com.example.llcgs.android_kotlin.architecture_components.room.menu.adapter.RoomMenuAdapter
import com.example.llcgs.android_kotlin.architecture_components.room.menu.db.MenuBean
import com.example.llcgs.android_kotlin.architecture_components.room.menu.presenter.IRoomMenuPresenter
import com.example.llcgs.android_kotlin.architecture_components.room.menu.presenter.impl.RoomMenuPresenter
import com.example.llcgs.android_kotlin.architecture_components.room.menu.view.RoomMenuView
import kotlinx.android.synthetic.main.activity_room_menu.*
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.menu.RoomMenuActivity
 * @author liulongchao
 * @since 2017/12/8
 */
class RoomMenuActivity : BaseOwnerActivity<IRoomMenuPresenter>(), RoomMenuView, BaseQuickAdapter.OnItemClickListener {

    private lateinit var array: List<String>
    private lateinit var menuList: List<MenuBean>
    private lateinit var adapter: RoomMenuAdapter

    override fun createPresenter(): IRoomMenuPresenter = RoomMenuPresenter(this)

    override fun getLayoutId(): Int = R.layout.activity_room_menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews() {
        pluginTitleTV.text = "RoomMenu"
        adapter = RoomMenuAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.onItemClickListener = this
    }

    private fun initData() {
        array = resources.getStringArray(R.array.arch_room_menu).toMutableList()
        mPresenter.switch(array)
    }

    override fun insertSuccess() {
        mPresenter.getAll()
    }

    override fun deleteSuccess() {
    }

    override fun getAllSuccess(list: List<MenuBean>) {
        menuList = list
        adapter.setNewData(list)
    }

    override fun switchSuccess(list: List<MenuBean>) {
        mPresenter.insert(list)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when(position){
            0 ->{
               startActivity(Intent(this, RoomActivity::class.java))
            }
            1 ->{

            }
        }
    }

    override fun onDestroy() {
        mPresenter.deleteAll(menuList)
        super.onDestroy()
    }
}