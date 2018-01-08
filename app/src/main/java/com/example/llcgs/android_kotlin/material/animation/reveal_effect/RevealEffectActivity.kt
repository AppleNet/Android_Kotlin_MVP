package com.example.llcgs.android_kotlin.material.animation.reveal_effect

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewAnimationUtils
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.animation.reveal_effect.presenter.IRevealEffectPresenter
import com.example.llcgs.android_kotlin.material.animation.reveal_effect.presenter.impl.RevealEffectPresenter
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import kotlinx.android.synthetic.main.activity_reveal_effect.*
import kotlinx.android.synthetic.main.view_material_toolbar.*

/**
 * com.example.llcgs.android_kotlin.material.animation.reveal_effect.RevealEffectActivity
 * @author liulongchao
 * @since 2018/1/5
 */
class RevealEffectActivity: BaseMaterialActivity<IRevealEffectPresenter>() {

    override fun createPresenter()= RevealEffectPresenter()

    override fun getLayoutId() = R.layout.activity_reveal_effect

    override fun initViews() {
        setSupportActionBar(toolbar)

        effect.setOnClickListener {

            val currentX = imageView.width/2
            val currentY = imageView.height/2

            /**
             *
                View view, 指定了为哪个View执行动画
                int centerX, 涟漪效果的中心x轴位置
                int centerY, 涟漪效果的中心y轴位置
                float startRadius, 开始的半径
                float endRadius, 结束的半径
             *
             * */
            val radius = Math.max(imageView.width, imageView.height)
            if (imageView.visibility == View.VISIBLE){
                val anim = ViewAnimationUtils.createCircularReveal(imageView, currentX, currentY, radius.toFloat(), 0F)
                anim.duration = 1000
                anim.addListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        imageView.visibility = View.INVISIBLE
                    }
                })
                anim.start()
            }else{
                val anim = ViewAnimationUtils.createCircularReveal(imageView, currentX, currentY, 0F, radius.toFloat())
                anim.duration = 1000
                imageView.visibility = View.VISIBLE
                anim.start()
            }

        }
    }
}