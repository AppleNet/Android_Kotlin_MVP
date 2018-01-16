package com.example.llcgs.android_kotlin.material.animation.path_animation

import android.animation.ObjectAnimator
import android.graphics.Path
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.animation.path_animation.presenter.IPathAnimationPresenter
import com.example.llcgs.android_kotlin.material.animation.path_animation.presenter.impl.PathAnimationPresenter
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import kotlinx.android.synthetic.main.activity_path_animation.*
import kotlinx.android.synthetic.main.view_material_toolbar.*

/**
 * com.example.llcgs.android_kotlin.material.animation.path_animation.PathAnimationActivity
 * @author liulongchao
 * @since 2018/1/16
 */
class PathAnimationActivity: BaseMaterialActivity<IPathAnimationPresenter>() {

    override fun createPresenter()= PathAnimationPresenter()

    override fun getLayoutId()= R.layout.activity_path_animation

    override fun initViews() {
        toolbar.title = intent.getStringExtra("name")
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(toolbar)
        kotlin.setOnClickListener {
            val path = Path()
            path.moveTo(100f, 100f)
            path.moveTo(100f, 100f)
            path.lineTo(600f, 100f)
            path.lineTo(100f, 600f)
            path.lineTo(600f, 600f)
            path.close()
            val anim = ObjectAnimator.ofFloat(it, View.X, View.Y, path)
            anim.duration = 1000
            anim.start()
        }
    }
}