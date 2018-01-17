package com.example.llcgs.android_kotlin.material.animation.svg

import android.graphics.drawable.Animatable
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.animation.svg.presenter.ISvgPresenter
import com.example.llcgs.android_kotlin.material.animation.svg.presenter.impl.SvgPresenter
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import kotlinx.android.synthetic.main.activity_svg.*
import kotlinx.android.synthetic.main.view_material_toolbar.*

/**
 * com.example.llcgs.android_kotlin.material.animation.svg.SvgActivity
 * @author liulongchao
 * @since 2018/1/17
 */
class SvgActivity: BaseMaterialActivity<ISvgPresenter>() {


    override fun createPresenter()= SvgPresenter()

    override fun getLayoutId()= R.layout.activity_svg

    override fun initViews() {
        toolbar.title = intent.getStringExtra("name")
        setSupportActionBar(toolbar)
        imageView.setOnClickListener {
            val d = imageView.drawable
            if (d is Animatable){
                d.start()
            }
        }
    }
}