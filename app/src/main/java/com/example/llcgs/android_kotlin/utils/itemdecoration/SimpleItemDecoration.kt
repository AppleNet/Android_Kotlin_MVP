package com.example.llcgs.android_kotlin.utils.itemdecoration

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View


/**
 * 作者：Alex
 * 时间：2016年12月12日
 * 简述：
 */
class SimpleItemDecoration private constructor(private val builder: Builder) : RecyclerView.ItemDecoration() {
    private var dividerDrawable: GradientDrawable? = null
    private var layoutType: Int = 0
    private var itemCount: Int = 0
    private var spanCount: Int = 0
    private var isPaddinged: Boolean = false

    init {
        initView(builder)
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        super.onDraw(canvas, parent, state)
        if (layoutType == LayoutType.VLinearLayout) {/*线性的 - 垂直滑动的子布局， 画 水平线*/
            drawHorizontalLine(canvas, parent, state)
        } else if (layoutType == LayoutType.HLinearLayout) {/*线性的 - 水平滑动的子布局， 画垂直线*/
            drawVerticalLine(canvas, parent, state)
        }
    }

    private fun initView(builder: Builder) {
        dividerDrawable = GradientDrawable()
        dividerDrawable!!.setColor(builder.color)
        layoutType = -1
        itemCount = -1
        spanCount = -1
        isPaddinged = false
    }

    /**
     * 这个方法比onDraw()先执行
     * 这里的作用是设置Rect的范围，
     * RecyclerView会调用这个方法来设置每个item view的padding值
     * Rect对应item view：
     * left=paddingLeft
     * right=paddingLeft
     * top=paddingTop
     * bottom=paddingBottom
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        //final int childCount = parent.getChildCount();
        if (layoutType == -1) {
            val layoutManager = parent.layoutManager
            if (layoutManager is GridLayoutManager) {
                spanCount = if (spanCount < 0) layoutManager.spanCount else spanCount
                layoutType = if (spanCount > 1) LayoutType.GridLayout else LayoutType.VLinearLayout
            } else if (layoutManager is StaggeredGridLayoutManager) {
                layoutType = if (OrientationHelper.HORIZONTAL == layoutManager.orientation) LayoutType.HStaggeredGridLayout else LayoutType.VStaggeredGridLayout
                spanCount = if (spanCount < 0) layoutManager.spanCount else spanCount
            } else {
                layoutType = if (OrientationHelper.VERTICAL == (layoutManager as LinearLayoutManager).orientation) LayoutType.VLinearLayout else LayoutType.HLinearLayout
                spanCount = 1
            }
        }

        val adapter = parent.adapter
        itemCount = adapter.itemCount

        val position = parent.getChildAdapterPosition(view)
        val paddFirst = 0
        val paddingLast = 0

        if (LayoutType.VLinearLayout == layoutType) {
            val paddingBottom = dp2px(view, builder.dividerSize)
            outRect.set(0, paddFirst, 0, paddingBottom)
        } else if (LayoutType.HLinearLayout == layoutType) {
            val paddingRight = dp2px(view, builder.dividerSize)
            outRect.set(paddFirst, 0, paddingRight, 0)
        } else if (LayoutType.GridLayout == layoutType) {
            val paddingV = dp2px(view, builder.dividerSize)
            val paddingH = dp2px(view, builder.dividerSize)
            if (spanCount == 2 && isFirstInRows(position)) {
                outRect.set(0, paddFirst, paddingH / 2, paddingV)
            } else if (spanCount == 2 && isLastInRows(position)) {
                outRect.set(paddingH / 2, paddFirst, 0, paddingV)
            } else if (spanCount == 3 && isFirstInRows(position)) {
                outRect.set(0, paddFirst, paddingH / 2, paddingV)
            } else if (spanCount == 3 && isMiddleInRows(position)) {
                outRect.set(paddingH / 4, paddFirst, paddingH / 4, paddingV)
            } else if (spanCount == 3 && isLastInRows(position)) {
                outRect.set(paddingH / 2, paddFirst, 0, paddingV)
            } else if (spanCount > 3 && isFirstInRows(position)) {
                outRect.set(paddingH / 2, paddFirst, paddingH / 2, paddingV)
            } else if (spanCount > 3 && isMiddleInRows(position)) {
                outRect.set(paddingH / 2, paddFirst, paddingH / 2, paddingV)
            } else if (spanCount > 3 && isLastInRows(position)) {
                outRect.set(paddingH / 2, paddFirst, paddingH / 2, paddingV)
            }
            if (spanCount > 3 && !isPaddinged) {
                isPaddinged = true
                parent.setPadding(paddingH / 2, parent.paddingTop, paddingH / 2, parent.paddingBottom)
                parent.clipToPadding = false
            }
        }
    }

    private fun drawHorizontalLine(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val childCount = parent.childCount
        for (i in 0..childCount - 1) {
            var left = parent.paddingLeft
            var right = parent.width - parent.paddingRight
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin + Math.round(ViewCompat.getTranslationY(child))
            val bottom = top + dp2px(parent, builder.dividerSize)
            if (LayoutType.VLinearLayout == layoutType || LayoutType.HLinearLayout == layoutType) {
                dividerDrawable!!.setBounds(left, top, right, bottom)
                dividerDrawable!!.setColor(builder.backgroundColor)
                dividerDrawable!!.draw(canvas)
                val position = parent.getChildAdapterPosition(child)
                if (isLastRows(position)) {
                    continue
                }
                val marginLeft = dp2px(parent, builder.marginLeft.toFloat())
                val marginRight = dp2px(parent, builder.marginRight.toFloat())
                left = left + marginLeft
                right = right - marginRight
                dividerDrawable!!.setBounds(left, top, right, bottom)
                dividerDrawable!!.setColor(builder.color)
                dividerDrawable!!.draw(canvas)
            } else if (LayoutType.GridLayout == layoutType) {/*网格 布局*/
                spanCount = if (spanCount <= 0) getSpanCount(parent) else spanCount
            }
        }
    }

    private fun drawVerticalLine(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val childCount = parent.childCount
        for (i in 0..childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin + Math.round(ViewCompat.getTranslationX(child))
            var top = parent.paddingTop
            val right = left + dp2px(parent, builder.dividerSize)
            var bottom = parent.height - parent.paddingBottom
            if (LayoutType.VLinearLayout == layoutType || LayoutType.HLinearLayout == layoutType) {
                val position = parent.getChildAdapterPosition(child)
                if (isLastRows(position)) {
                    continue
                }
                dividerDrawable!!.setBounds(left, top, right, bottom)
                dividerDrawable!!.setColor(builder.backgroundColor)
                dividerDrawable!!.draw(canvas)
                val marginV = dp2px(parent, builder.marginV.toFloat())
                top += marginV
                bottom -= marginV
                dividerDrawable!!.setBounds(left, top, right, bottom)
                dividerDrawable!!.setColor(builder.color)
                dividerDrawable!!.draw(canvas)

            } else if (LayoutType.GridLayout == layoutType) {/*网格 布局*/

            }

        }
    }

    /**
     * 获取 网格多少列
     */
    private fun getSpanCount(parent: RecyclerView): Int {
        if (parent.layoutManager is GridLayoutManager) {
            val layoutManager = parent.layoutManager as GridLayoutManager
            return layoutManager.spanCount
        }
        return 1
    }

    /**
     * 获取 行数
     *
     * @return
     */
    private val rowsCount: Int
        get() {
            if (spanCount == 1) {
                return itemCount
            }
            val offset = itemCount % spanCount
            val rowsCount = itemCount / spanCount
            return if (offset == 0) rowsCount else rowsCount + 1
        }

    /**
     * 获取 position  在第几排， 下标 从 0 开始
     *
     * @param position
     * @return
     */
    private fun getRowsIndex(position: Int): Int {
        return position / spanCount
    }

    /**
     * 在 最后 一排
     */
    private fun isFirstRows(position: Int): Boolean {
        return getRowsIndex(position) == 0
    }

    /**
     * 在 最后 一排
     */
    private fun isLastRows(position: Int): Boolean {
        return getRowsIndex(position) == rowsCount - 1
    }


    /**
     * 是当前行 的 第一个
     */
    fun isFirstInRows(position: Int): Boolean {
        return if (spanCount <= 1) {
            true
        } else position % spanCount == 0
    }

    /**
     * 是当前行 的 最后一个
     */
    private fun isLastInRows(position: Int): Boolean {
        return if (spanCount <= 1) {
            true
        } else position % spanCount == spanCount - 1
    }

    /**
     * 是当前行 的 中间一个
     */
    private fun isMiddleInRows(position: Int): Boolean {
        if (spanCount <= 1) {
            return true
        }
        if (spanCount == 2) {
            return false
        }
        val offset = position % spanCount
        return (offset > 0) and (offset < spanCount - 1)
    }

    /**
     * RecyclerView 关联 分割线类型
     *
     * @param recyclerView
     */
    fun attachToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addItemDecoration(this)
    }

    class Builder private constructor() {
        var color: Int = 0
        var backgroundColor: Int = 0
        var dividerSize: Float = 0.toFloat()
        var marginLeft: Int = 0
        var marginRight: Int = 0
        var marginV: Int = 0

        fun backgroundColor(color: Int): Builder {
            this.backgroundColor = color
            return this
        }

        fun backgroundColor(color: String): Builder {
            return backgroundColor(Color.parseColor(color))
        }

        fun color(color: Int): Builder {
            this.color = color
            return this
        }

        fun color(color: String): Builder {
            return color(Color.parseColor(color))
        }

        /**
         * 单位 dp
         */
        fun dividerSize(size: Float): Builder {
            this.dividerSize = size
            return this
        }

        /**
         * 单位 dp，
         * <br></br>
         * this.marginLeft = margin[0];
         * <br></br>
         * this.marginRight = (margin.length == 1) ? margin[0] : margin[1];
         */
        fun marginH(vararg margin: Int): Builder {
            if (margin == null) {
                this.marginLeft = 0
                this.marginRight = 0
            }
            this.marginLeft = margin[0]
            this.marginRight = if (margin.size == 1) margin[0] else margin[1]
            return this
        }

        /**
         * 单位 dp
         */
        fun marginV(marginV: Int): Builder {
            this.marginV = marginV
            return this
        }

        fun build(): SimpleItemDecoration {
            return SimpleItemDecoration(this)
        }

        companion object {

            val instance: Builder
                get() = Builder()
        }
    }

    /**
     * 数据转换: dp---->px
     */
    private fun dp2px(view: View?, dp: Float): Int {
        return if (view == null) {
            -1
        } else (dp * view.context.resources.displayMetrics.density).toInt()
    }

}
