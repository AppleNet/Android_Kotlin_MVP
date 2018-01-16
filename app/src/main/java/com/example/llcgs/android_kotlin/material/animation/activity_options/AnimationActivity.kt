package com.example.llcgs.android_kotlin.material.animation.activity_options

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import com.example.llcgs.android_kotlin.R
import kotlinx.android.synthetic.main.activity_animation.*
import kotlinx.android.synthetic.main.view_material_toolbar.*

/**
 * com.example.llcgs.android_kotlin.material.animation.activity_options.AnimationActivity
 * @author liulongchao
 * @since 2018/1/8
 */
class AnimationActivity : AppCompatActivity(){

    private var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        val transitionName = intent.getStringExtra("transitionName")
        if(transitionName.isNotEmpty()){
            ViewCompat.setTransitionName(transitionImg, transitionName)
        }
        name = intent.getStringExtra("AnimationName")
        toolbar.title = name
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(toolbar)
        textView5.text = name

        //
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (name == "makeCustomAnimation"){
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
        }
    }

}