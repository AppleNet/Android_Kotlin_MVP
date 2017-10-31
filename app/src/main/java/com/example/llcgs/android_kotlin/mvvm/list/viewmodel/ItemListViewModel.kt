package com.example.llcgs.android_kotlin.mvvm.list.viewmodel

import android.content.Intent
import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.list.model.Student
import com.example.llcgs.android_kotlin.mvvm.show.PhotoShowActivity

/**
 * com.example.llcgs.android_kotlin.mvvm.list.viewmodel.ItemListViewModel
 * @author liulongchao
 * @since 2017/10/26
 */


class ItemListViewModel: BaseViewModel() {

    var student = Student()

    /*
    *  item 点击事件
    *  Method References
    * */
    fun onItemClickListener(view: View){
        Toast.makeText(view.context, student.fullName, Toast.LENGTH_LONG).show()
    }

    /**
     *  图片点击事件
     * */
    fun onImageViewClickListener(view: View){
        //
        val intent = Intent(view.context, PhotoShowActivity::class.java).apply {
            putExtra("imageUrl", student.picture.medium)
        }
        view.context.startActivity(intent)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageUrl(imageView: ImageView, url: String) {
            Glide.with(imageView.context).load(url).into(imageView)
        }
    }



}