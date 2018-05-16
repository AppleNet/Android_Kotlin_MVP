package com.example.llcgs.android_kotlin.material.animation.activity_options

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.animation.activity_options.presenter.IActivityOptionsPresenter
import com.example.llcgs.android_kotlin.material.animation.activity_options.presenter.impl.ActivityOptionsPresenter
import com.example.llcgs.android_kotlin.material.animation.activity_options.view.ActivityOptionsView
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import kotlinx.android.synthetic.main.activity_reveal.*

/**
 * com.example.llcgs.android_kotlin.material.animation.activity_options.RevealActivity
 * @author liulongchao
 * @since 2018/1/18
 */
class RevealActivity: BaseMaterialActivity<IActivityOptionsPresenter>(), ActivityOptionsView {

    private lateinit var interpolator: Interpolator

    override fun createPresenter()= ActivityOptionsPresenter(this)

    override fun getLayoutId()= R.layout.activity_reveal

    override fun initViews() {
        setupWindowAnimations()
        toolbarTitle.text = intent.getStringExtra("AnimationName")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupWindowAnimations(){
        interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in)
        setupEnterAnimations()
        setupExitAnimations()
    }

    private fun setupEnterAnimations(){
        val transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion)
        window.sharedElementEnterTransition = transition
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionResume(transition: Transition?) {
            }

            override fun onTransitionPause(transition: Transition?) {
            }

            override fun onTransitionCancel(transition: Transition?) {
            }

            override fun onTransitionStart(transition: Transition?) {
            }

            override fun onTransitionEnd(transition: Transition?) {
                //
                transition?.removeListener(this)
                animateRevealShow(toolbar)
                animateButtonsIn()
                shared_target.visibility = View.GONE
            }
        })
    }

    private fun setupExitAnimations(){
        val returnTransition = Fade()
        window.returnTransition = returnTransition
        returnTransition.duration = 300
        returnTransition.startDelay = 300
        returnTransition.addListener(object : Transition.TransitionListener{
            override fun onTransitionEnd(transition: Transition?) {
                transition?.removeListener(this)
                // animateButtonOut()
                // animateRevealHide()
            }

            override fun onTransitionResume(transition: Transition?) {
            }

            override fun onTransitionPause(transition: Transition?) {
            }

            override fun onTransitionCancel(transition: Transition?) {
            }

            override fun onTransitionStart(transition: Transition?) {
            }
        })
    }

    private fun animateRevealShow(viewRoot: View){
        val cx = (viewRoot.left + viewRoot.right) / 2
        val cy = (viewRoot.top + viewRoot.bottom) / 2
        val finalRadius = Math.max(viewRoot.width, viewRoot.height)

        val anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0f, finalRadius.toFloat())
        viewRoot.visibility = View.VISIBLE
        anim.duration = 500
        anim.interpolator = AccelerateInterpolator()
        anim.start()
    }

    private fun animateRevealHide(viewRoot: View){
        val cx = (viewRoot.left + viewRoot.right) / 2
        val cy = (viewRoot.top + viewRoot.bottom) / 2
        val initialRadius = viewRoot.width

        val anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, initialRadius.toFloat(), 0f)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                viewRoot.visibility = View.INVISIBLE
            }
        })
        anim.duration = 300
        anim.start()
    }

    private fun animateButtonsIn(){

    }

    private fun animateButtonOut(){

    }

    override fun onGetActivityOptions(list: List<String>) {
    }

    override fun onGetActivityTransition(list: List<String>) {
    }
}