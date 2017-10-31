package com.example.llcgs.android_kotlin.mvvm.list.viewmodel

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.list.model.Student

/**
 * com.example.llcgs.android_kotlin.mvvm.list.viewmodel.ItemListViewModel
 * @author liulongchao
 * @since 2017/10/26
 */


class ItemListViewModel: BaseViewModel() {

    var student = Student()

    /*
    *  item 点击事件
    * */
    fun onItemClickListener(view: View){
        Toast.makeText(view.context, student.fullName, Toast.LENGTH_LONG).show()
    }


    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageUrl(imageView: ImageView, url: String) {
            Glide.with(imageView.context).load(url).into(imageView)
        }
    }



}