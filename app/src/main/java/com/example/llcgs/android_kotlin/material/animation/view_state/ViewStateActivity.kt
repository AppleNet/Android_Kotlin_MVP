package com.example.llcgs.android_kotlin.material.animation.view_state

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.animation.view_state.presenter.IViewStatePresenter
import com.example.llcgs.android_kotlin.material.animation.view_state.presenter.impl.ViewStatePresenter
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import kotlinx.android.synthetic.main.view_material_toolbar.*

/**
 * com.example.llcgs.android_kotlin.material.animation.view_state.ViewStateActivity
 * @author liulongchao
 * @since 2018/1/16
 */
class ViewStateActivity : BaseMaterialActivity<IViewStatePresenter>() {

    override fun createPresenter() = ViewStatePresenter()

    override fun getLayoutId()= R.layout.activity_view_state

    override fun initViews() {
        toolbar.title = intent.getStringExtra("name")
        setSupportActionBar(toolbar)

        // 使用android:stateListAnimator 将设置的自定义drawable 设置给ImageView

    }
}