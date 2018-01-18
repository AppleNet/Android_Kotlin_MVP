package com.example.llcgs.android_kotlin.material.douya.gallery.presenter

import android.content.Context
import android.support.v7.widget.Toolbar
import com.example.llcgs.android_kotlin.material.base.BaseMaterialPresenter
import me.zhanghai.android.systemuihelper.SystemUiHelper

/**
 * com.example.llcgs.android_kotlin.material.gallery.presenter.IGalleryPresenter
 * @author liulongchao
 * @since 2017/12/18
 */
interface IGalleryPresenter : BaseMaterialPresenter {

    fun setSystemUiHelper(context: Context, toolbar: Toolbar)
}