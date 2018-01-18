package com.example.llcgs.android_kotlin.material.douya.gallery.adapter

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.utils.ViewUtils
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_gallery_item.*

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
        val holder = com.example.llcgs.android_kotlin.material.douya.gallery.adapter.GalleryAdapter.Holder(view)
        view.tag = holder
        holder.image.setOnClickListener {
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

    private fun loadImageForPosition(container: ViewGroup, position: Int, holder: com.example.llcgs.android_kotlin.material.douya.gallery.adapter.GalleryAdapter.Holder) {
        ViewUtils.fadeIn(holder.progress)
        Glide.with(container.context).load(mImageList[position]).into(holder.image)
        holder.image.visibility = View.VISIBLE
        ViewUtils.fadeOut(holder.progress)
    }

    class Holder(override val containerView: View?): LayoutContainer
}