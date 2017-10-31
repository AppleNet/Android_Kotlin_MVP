package com.example.llcgs.android_kotlin.mvvm.show.viewmodel

import android.databinding.BindingAdapter
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel

/**
 * com.example.llcgs.android_kotlin.mvvm.show.viewmodel.PhotoShowViewModel
 * @author liulongchao
 * @since 2017/10/31
 */


class PhotoShowViewModel: BaseViewModel() {

    var imageUrl: String = ""
        get() = "http://cdn.meme.am/instances/60677654.jpg"

    companion object {
        @JvmStatic
        @BindingAdapter("showImage")
        fun showImage(imageView: ImageView, url: String){
            if (!TextUtils.isEmpty(url)){
                Glide.with(imageView.context).load(url).into(imageView)
            }
        }
    }

}