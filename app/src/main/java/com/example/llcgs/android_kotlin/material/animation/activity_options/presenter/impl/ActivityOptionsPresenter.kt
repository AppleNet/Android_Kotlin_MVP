package com.example.llcgs.android_kotlin.material.animation.activity_options.presenter.impl

import com.example.llcgs.android_kotlin.material.animation.activity_options.model.ActivityOptionsModel
import com.example.llcgs.android_kotlin.material.animation.activity_options.presenter.IActivityOptionsPresenter
import com.example.llcgs.android_kotlin.material.animation.activity_options.view.ActivityOptionsView

/**
 * com.example.llcgs.android_kotlin.material.animation.activity_options.presenter.impl.ActivityOptionsPresenter
 * @author liulongchao
 * @since 2018/1/8
 */
class ActivityOptionsPresenter(private val view: ActivityOptionsView) : IActivityOptionsPresenter {

    private val model = ActivityOptionsModel()

    override fun getActivityOptions() {
        model.getActivityOptions()
                .subscribe {
                    view.onGetActivityOptions(it)
                }
    }

}