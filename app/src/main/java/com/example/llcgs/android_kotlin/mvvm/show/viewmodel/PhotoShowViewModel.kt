package com.example.llcgs.android_kotlin.mvvm.show.viewmodel

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel

/**
 * com.example.llcgs.android_kotlin.mvvm.show.viewmodel.PhotoShowViewModel
 * @author liulongchao
 * @since 2017/10/31
 */


class PhotoShowViewModel: BaseViewModel() {

    var url: String = ""

    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun setImageUrl(imageView: ImageView, url: String){
            Glide.with(imageView.context).load(url).into(imageView)
        }
    }

}