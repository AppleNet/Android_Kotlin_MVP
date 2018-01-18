package com.example.llcgs.android_kotlin.material.douya.gallery.presenter.impl

import android.content.Context
import android.support.v7.widget.Toolbar
import com.example.llcgs.android_kotlin.material.douya.gallery.model.GalleryModel
import com.example.llcgs.android_kotlin.material.douya.gallery.presenter.IGalleryPresenter
import com.example.llcgs.android_kotlin.material.douya.gallery.view.GalleryView

/**
 * com.example.llcgs.android_kotlin.material.gallery.presenter.impl.GalleryPresenter
 * @author liulongchao
 * @since 2017/12/18
 */
class GalleryPresenter(private val view: com.example.llcgs.android_kotlin.material.douya.gallery.view.GalleryView): com.example.llcgs.android_kotlin.material.douya.gallery.presenter.IGalleryPresenter {

    private val model = com.example.llcgs.android_kotlin.material.douya.gallery.model.GalleryModel()

    override fun setSystemUiHelper(context: Context, toolbar: Toolbar) {
        model.setSystemUiHelper(context, toolbar)
                .subscribe {
                    view.onGetSystemUiHelper(it)
                }
    }

}