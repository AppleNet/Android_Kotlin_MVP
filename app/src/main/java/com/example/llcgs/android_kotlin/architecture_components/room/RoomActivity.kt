package com.example.llcgs.android_kotlin.architecture_components.room

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.base.BaseOwnerActivity
import com.example.llcgs.android_kotlin.architecture_components.base.rx.LifeCycleRx
import com.example.llcgs.android_kotlin.architecture_components.room.adapter.RoomAdapter
import com.example.llcgs.android_kotlin.architecture_components.room.db.db_bean.Notice
import com.example.llcgs.android_kotlin.architecture_components.room.db.database.AppDatabase
import com.example.llcgs.android_kotlin.architecture_components.room.presenter.IRoomPresenter
import com.example.llcgs.android_kotlin.architecture_components.room.presenter.impl.RoomPresenter
import com.example.llcgs.android_kotlin.architecture_components.room.view.RoomView
import com.example.llcgs.android_kotlin.utils.DBUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_room.*

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.RoomActivity
 * @author liulongchao
 * @since 2017/12/6
 */
class RoomActivity : BaseOwnerActivity<IRoomPresenter>(), View.OnClickListener, RoomView {

    private lateinit var adapter: RoomAdapter

    override fun createPresenter(): IRoomPresenter = RoomPresenter()

    override fun getLayoutId(): Int = R.layout.activity_room

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            // add

        }

        adapter = RoomAdapter()
        adapter.emptyView = TextView(this).apply { text = "暂无数据" }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun initData() {
        DBUtils.getDBService(AppDatabase::class.java)
                .noticeDao()
                .getAll()
                .doOnSubscribe(::addDisposable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(LifeCycleRx.bindLifecycle(this))
                .subscribe(::addData)
    }

    override fun onClick(v: View?) {

    }

    override fun onInput(dialog: MaterialDialog, input: CharSequence){

    }

    private fun addData(data: List<Notice>) {
        adapter.setNewData(data)
    }

}