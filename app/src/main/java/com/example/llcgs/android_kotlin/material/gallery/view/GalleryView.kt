package com.example.llcgs.android_kotlin.material.gallery.view

import com.example.llcgs.android_kotlin.material.base.BaseMaterialView
import me.zhanghai.android.systemuihelper.SystemUiHelper

/**
 * com.example.llcgs.android_kotlin.material.gallery.view.GalleryView
 * @author liulongchao
 * @since 2017/12/18
 */
interface GalleryView: BaseMaterialView {

    fun onGetSystemUiHelper(systemUiHelper: SystemUiHelper)
}