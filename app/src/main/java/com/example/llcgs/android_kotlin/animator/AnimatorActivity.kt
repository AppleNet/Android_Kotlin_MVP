package com.example.llcgs.android_kotlin.animator

import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.animator.presenter.impl.AnimatorPresenter
import com.example.llcgs.android_kotlin.animator.view.AnimatorView
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.lzh.nonview.router.anno.RouterRule

/**
 * com.example.llcgs.android_kotlin.animator.AnimatorActivity
 * @author liulongchao
 * @since 2018/9/29
 */
@RouterRule("Animator")
class AnimatorActivity: BaseActivity<AnimatorView, AnimatorPresenter>() {

    override fun createPresenter()= AnimatorPresenter()

    /**
     *  属性动画
     *      ObjectAnimator
     *      ValueAnimator
     *      Animator
     * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator)
    }
}