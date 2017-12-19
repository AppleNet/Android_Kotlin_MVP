package com.example.llcgs.android_kotlin.material.gallery.model

import android.app.Activity
import android.content.Context
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v7.widget.Toolbar
import com.example.llcgs.android_kotlin.material.base.BaseMaterialModel
import io.reactivex.Observable
import me.zhanghai.android.systemuihelper.SystemUiHelper

/**
 * com.example.llcgs.android_kotlin.material.gallery.model.GalleryModel
 * @author liulongchao
 * @since 2017/12/18
 */
class GalleryModel: BaseMaterialModel {

    fun setSystemUiHelper(context: Context, toolbar: Toolbar): Observable<SystemUiHelper>{
        val mSystemUiHelper = SystemUiHelper(context as Activity, SystemUiHelper.LEVEL_IMMERSIVE, SystemUiHelper.FLAG_IMMERSIVE_STICKY, SystemUiHelper.OnVisibilityChangeListener { visible ->
            if (visible) {
                toolbar.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(400)
                        .setInterpolator(FastOutSlowInInterpolator())
                        .start()
            }else{
                toolbar.animate()
                        .alpha(0f)
                        .translationY(-toolbar.bottom.toFloat())
                        .setDuration(400)
                        .setInterpolator(FastOutSlowInInterpolator())
                        .start()
            }
        })
        return Observable.just(mSystemUiHelper)
    }
}