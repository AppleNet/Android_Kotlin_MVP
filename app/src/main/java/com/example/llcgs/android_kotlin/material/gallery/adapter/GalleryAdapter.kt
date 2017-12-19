package com.example.llcgs.android_kotlin.material.gallery.adapter

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.utils.ViewUtils
import me.zhanghai.android.materialprogressbar.MaterialProgressBar

/**
 * com.example.llcgs.android_kotlin.material.gallery.adapter.GalleryAdapter
 * @author liulongchao
 * @since 2017/12/19
 */
class GalleryAdapter : PagerAdapter() {

    var mImageList = ArrayList<String>()
    private var listener: (()->Unit)? = null

    fun setListener(listener: ()->Unit){
        this.listener = listener
    }

    override fun isViewFromObject(view: View, any: Any) = view == any

    override fun getCount() = mImageList.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any =
            onCreateView(container, position)

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        if (any is View){
            onDestroyView(container, any)
        }
    }

    private fun onCreateView(container: ViewGroup, position: Int): View {
        val view = LayoutInflater.from(container.context).inflate(R.layout.view_gallery_item, null)
        val holder = Holder(view)
        view.tag = holder
        holder.imageView.setOnClickListener {
            listener?.let {
                it
            }
        }
        holder.largeImage.setOnClickListener {
            listener?.let {
                it
            }
        }
        loadImageForPosition(container, position, holder)
        container.addView(view)
        return view
    }

    private fun onDestroyView(container: ViewGroup, view: View){
        container.removeView(view)
    }

    private fun loadImageForPosition(container: ViewGroup, position: Int, holder: Holder) {
        ViewUtils.fadeIn(holder.progress)
        Glide.with(container.context).load(mImageList[position]).into(holder.imageView)
        holder.imageView.visibility = View.VISIBLE
        ViewUtils.fadeOut(holder.progress)
    }

    class Holder(view: View) {
        val imageView: ImageView = view.findViewById(R.id.image)
        val largeImage: ImageView = view.findViewById(R.id.largeImage)
        val errorText: TextView = view.findViewById(R.id.error)
        val progress: MaterialProgressBar = view.findViewById(R.id.progress)
    }
}