package com.example.llcgs.android_kotlin.material.animation.activity_options

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Fade
import android.transition.Visibility
import com.example.llcgs.android_kotlin.R
import kotlinx.android.synthetic.main.activity_animation.*
import kotlinx.android.synthetic.main.view_material_toolbar.*

/**
 * com.example.llcgs.android_kotlin.material.animation.activity_options.AnimationActivity
 * @author liulongchao
 * @since 2018/1/8
 */
class AnimationTransitionActivity : AppCompatActivity(){

    private var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition)
        window.enterTransition = buildEnterTransition()

        toolbar.title = intent.getStringExtra("AnimationName")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun buildEnterTransition() = Fade().apply {
        duration = 500
        excludeTarget(R.id.imageView, true)
    }

}