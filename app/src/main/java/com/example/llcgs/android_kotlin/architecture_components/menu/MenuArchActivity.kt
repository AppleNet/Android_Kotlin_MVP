package com.example.llcgs.android_kotlin.architecture_components.menu

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.base.BaseOwnerActivity
import com.example.llcgs.android_kotlin.architecture_components.lifecycle.LifeCycleActivity
import com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.ExtendLiveDataActivity
import com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.TransformLiveDataActivity
import com.example.llcgs.android_kotlin.architecture_components.livedata.use_livedata.LiveDataActivity
import com.example.llcgs.android_kotlin.architecture_components.menu.adapter.MenuArchAdapter
import com.example.llcgs.android_kotlin.architecture_components.menu.presenter.IMenuArchPresenter
import com.example.llcgs.android_kotlin.architecture_components.menu.presenter.impl.MenuArchPresenter
import com.example.llcgs.android_kotlin.architecture_components.room.RoomActivity
import com.example.llcgs.android_kotlin.architecture_components.viewmodel.share_viewmodel.ShareViewModelActivity
import com.example.llcgs.android_kotlin.architecture_components.viewmodel.use_viewmodel.ViewModelActivity
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.activity_menu_arch.*
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.architecture_components.menu.MenuArchActivity
 * @author liulongchao
 * @since 2017/11/29
 */
@RouterRule("Architecture")
class MenuArchActivity : BaseOwnerActivity<IMenuArchPresenter>(), BaseQuickAdapter.OnItemClickListener {

    private lateinit var adapter: MenuArchAdapter

    override fun createPresenter(): IMenuArchPresenter = MenuArchPresenter()

    override fun getLayoutId(): Int = R.layout.activity_menu_arch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        pluginTitleTV.text = "Architecture"
        adapter = MenuArchAdapter()
        adapter.onItemClickListener = this
        adapter.setNewData(resources.getStringArray(R.array.arch_menu).toMutableList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when(position){
            0 ->{
                startActivity(Intent(this@MenuArchActivity, LifeCycleActivity::class.java))
            }
            1 ->{
                startActivity(Intent(this@MenuArchActivity, LiveDataActivity::class.java))
            }
            2 ->{
                startActivity(Intent(this@MenuArchActivity, ExtendLiveDataActivity::class.java))
            }
            3 ->{
                startActivity(Intent(this@MenuArchActivity, TransformLiveDataActivity::class.java))
            }
            4 ->{
                startActivity(Intent(this@MenuArchActivity, ViewModelActivity::class.java))
            }
            5 ->{
                startActivity(Intent(this@MenuArchActivity, ShareViewModelActivity::class.java))
            }
            6 ->{
                startActivity(Intent(this@MenuArchActivity, RoomActivity::class.java))
            }
        }
    }

}