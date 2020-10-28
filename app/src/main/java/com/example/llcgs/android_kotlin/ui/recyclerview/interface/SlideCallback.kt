package com.example.llcgs.android_kotlin.ui.recyclerview.`interface`

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.llcgs.android_kotlin.ui.recyclerview.adapter.RecyclerViewAdapter
import com.example.llcgs.android_kotlin.ui.recyclerview.bean.Start
import com.example.llcgs.android_kotlin.ui.recyclerview.layoutmanager.CardConfig

/**
 * com.example.llcgs.android_kotlin.ui.recyclerview.interface.SlideCallback
 *
 * @author liulongchao
 * @since 2020/10/27
 */
class SlideCallback(val recyclerView: RecyclerView, val adapter: RecyclerViewAdapter, val mDatas: ArrayList<Start>)
    : ItemTouchHelper.SimpleCallback(0, 15) /* 拖拽 和 滑动方向 */ {

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?)= false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        val remove: Start  = mDatas.removeAt(viewHolder.layoutPosition)
        mDatas.add(0, remove)
        adapter.notifyDataSetChanged()

    }

    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val maxDistance = (0.5f * recyclerView.width).toDouble()
        val distance = Math.sqrt((dX * dY + dY * dY).toDouble())
        var fraction = distance / maxDistance

        if (fraction > 1) {
            fraction = 1.0
        }

        val itemCount = recyclerView.childCount
        for (i in 0 .. itemCount) {
            val view = recyclerView.getChildAt(i)
            val level = itemCount - i -1
            if (level > 0) {
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    view.translationY = (CardConfig.TRANS_Y_GAP * level - fraction * CardConfig.TRANS_Y_GAP).toFloat()
                    view.scaleX = (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP).toFloat()
                    view.scaleY = (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP).toFloat()
                }
            }
        }
    }

    /**
     *  设置回弹时间
     *
     * @param recyclerView
     * @param animationType
     * @param animateDx
     * @param animateDy
     * */
    override fun getAnimationDuration(recyclerView: RecyclerView?, animationType: Int, animateDx: Float, animateDy: Float)= 500L

}