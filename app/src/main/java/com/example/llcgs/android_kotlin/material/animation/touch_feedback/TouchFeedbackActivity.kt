package com.example.llcgs.android_kotlin.material.animation.touch_feedback

import android.content.Intent
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.animation.activity_options.ActivityOptionsActivity
import com.example.llcgs.android_kotlin.material.animation.path_animation.PathAnimationActivity
import com.example.llcgs.android_kotlin.material.animation.reveal_effect.RevealEffectActivity
import com.example.llcgs.android_kotlin.material.animation.touch_feedback.presenter.ITouchFeedbackPresenter
import com.example.llcgs.android_kotlin.material.animation.touch_feedback.presenter.impl.TouchFeedbackPresenter
import com.example.llcgs.android_kotlin.material.animation.view_state.ViewStateActivity
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.activity_touch_feedback.*
import kotlinx.android.synthetic.main.view_material_toolbar.*

/**
 * com.example.llcgs.android_kotlin.material.animation.touch_feedback.TouchFeedbackActivity
 * @author liulongchao
 * @since 2018/1/5
 */
@RouterRule("Material_Animation")
class TouchFeedbackActivity: BaseMaterialActivity<ITouchFeedbackPresenter>() {

    override fun createPresenter()= TouchFeedbackPresenter()

    override fun getLayoutId()= R.layout.activity_touch_feedback

    override fun initViews() {
        setSupportActionBar(toolbar)
        /**
         *
         *  <!--
                selectableItemBackground代表了当点击后会在当前Button区域发生一个涟漪效果，
                而selectableItemBackgroundBorderless的效果不会局限于Button本身的大小
                -->
         * */
        materialBorder.setOnClickListener {
            startActivity(Intent(this, RevealEffectActivity::class.java))
        }

        materialBorderless.setOnClickListener {
            startActivity(Intent(this, ActivityOptionsActivity::class.java))
        }

        pathAnimation.setOnClickListener {
            startActivity(Intent(this, PathAnimationActivity::class.java).apply {
                putExtra("name", "pathAnimation")
            })
        }

        viewState.setOnClickListener {
            startActivity(Intent(this, ViewStateActivity::class.java).apply {
                putExtra("name", "viewState")
            })
        }

        dialogRecyclerView.setOnClickListener {

        }
    }
}