package com.example.llcgs.android_kotlin.architecture_components.room.demo

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.alibaba.android.kotlin.showInputMaterialDialog
import com.alibaba.android.kotlin.showMessageMaterialDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.base.BaseOwnerActivity
import com.example.llcgs.android_kotlin.architecture_components.room.adapter.RoomAdapter
import com.example.llcgs.android_kotlin.architecture_components.room.demo.db.bean.Notice
import com.example.llcgs.android_kotlin.architecture_components.room.presenter.IRoomPresenter
import com.example.llcgs.android_kotlin.architecture_components.room.presenter.impl.RoomPresenter
import com.example.llcgs.android_kotlin.architecture_components.room.view.RoomView
import kotlinx.android.synthetic.main.activity_room.*

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.demo.RoomActivity
 * @author liulongchao
 * @since 2017/12/6
 */
class RoomActivity : BaseOwnerActivity<IRoomPresenter>(), View.OnClickListener, RoomView, BaseQuickAdapter.OnItemLongClickListener, MaterialDialog.InputCallback, MaterialDialog.SingleButtonCallback {

    private lateinit var adapter: RoomAdapter
    private lateinit var notice: Notice

    override fun createPresenter(): IRoomPresenter = RoomPresenter(this)

    override fun getLayoutId(): Int = R.layout.activity_room

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.parseColor("#000000"))
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            showInputMaterialDialog(context = this, callback = this)
        }

        adapter = RoomAdapter()
        adapter.emptyView = LayoutInflater.from(this).inflate(R.layout.view_empty_ry, null)
        adapter.onItemLongClickListener = this
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    // query
    private fun initData() {
        mPresenter.getAll()
    }

    // insert
    override fun onInput(dialog: MaterialDialog, input: CharSequence) {
        mPresenter.input(dialog, input)
    }

    // delete
    override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int): Boolean {
        notice = adapter?.data?.get(position) as Notice
        showMessageMaterialDialog(context = this, content = "确认删除?", callback = this)
        return true
    }

    override fun onClick(dialog: MaterialDialog, which: DialogAction) {
        when (which) {
            DialogAction.POSITIVE -> {
                mPresenter.delete(notice)
            }
            else -> {
            }
        }
    }

    override fun onGetAll(data: List<Notice>) {
        adapter.setNewData(data)
    }

    override fun onInputSuccess() {
        initData()
    }

    override fun onDeleteSuccess() {
        initData()
    }

    override fun onUpdateSuccess() {
        initData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_settings ->{
                //

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {

    }
}