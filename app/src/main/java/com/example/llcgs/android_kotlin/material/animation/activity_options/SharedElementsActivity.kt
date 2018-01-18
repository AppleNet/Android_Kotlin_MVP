package com.example.llcgs.android_kotlin.material.animation.activity_options

import android.support.v4.view.ViewCompat
import android.transition.ChangeBounds
import android.transition.Slide
import android.view.Gravity
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.animation.activity_options.presenter.IActivityOptionsPresenter
import com.example.llcgs.android_kotlin.material.animation.activity_options.presenter.impl.ActivityOptionsPresenter
import com.example.llcgs.android_kotlin.material.animation.activity_options.view.ActivityOptionsView
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import kotlinx.android.synthetic.main.activity_shared_elements.*

/**
 * com.example.llcgs.android_kotlin.material.animation.activity_options.SharedElementsActivity
 * @author liulongchao
 * @since 2018/1/17
 */
class SharedElementsActivity: BaseMaterialActivity<IActivityOptionsPresenter>(), ActivityOptionsView {

    override fun createPresenter() = ActivityOptionsPresenter(this)

    override fun getLayoutId()= R.layout.activity_shared_elements

    override fun initViews() {
        setTransitionName()
        setupAnimations()
        setupToolbar()
    }

    private fun setTransitionName() {
        ViewCompat.setTransitionName(shareCircleImageView, "circleImageView")
        ViewCompat.setTransitionName(toolbarTitle, "textView5")
    }

    private fun setupToolbar() {
        toolbarTitle.text = intent.getStringExtra("AnimationName")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupAnimations() {
        window.enterTransition.duration = 500
        val slideTransition = Slide()
        slideTransition.slideEdge = Gravity.START
        slideTransition.duration = 500
        window.reenterTransition = slideTransition
        window.exitTransition = slideTransition
        window.sharedElementEnterTransition = ChangeBounds()
    }

    override fun onGetActivityOptions(list: List<String>) {

    }

    override fun onGetActivityTransition(list: List<String>) {
    }
}