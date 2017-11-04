package com.example.llcgs.android_kotlin.mvvm.show.viewmodel

import android.content.Intent
import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.layoutdetails.LayoutDetailsActivity

/**
 * com.example.llcgs.android_kotlin.mvvm.show.viewmodel.PhotoShowViewModel
 * @author liulongchao
 * @since 2017/10/31
 */


class PhotoShowViewModel: BaseViewModel() {

    var url: String = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509790203568&di=8cbc7c7d4d9df9dc64c6d37f32be7967&imgtype=0&src=http%3A%2F%2Fnews.qiyue.com%2Fuploadfile%2F2016%2F0525%2F20160525104020138.jpg"
        set(value) {
            if (value.isNotEmpty()){
                field = value
                setChanged()
                notifyObservers()
            }
        }

    @Command
    fun onImageClickListener()= View.OnClickListener{
        Toast.makeText(it.context, "url: $url", Toast.LENGTH_SHORT).show()
        it.context.startActivity(Intent(it.context, LayoutDetailsActivity::class.java))
    }

    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun setImageUrl(imageView: ImageView, url: String){
            Glide.with(imageView.context).load(url).into(imageView)
        }
    }

}