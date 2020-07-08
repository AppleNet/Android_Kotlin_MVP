package com.alibaba.android.vlayout

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import java.lang.reflect.Method

/**
 * com.alibaba.android.vlayout.ExposeLinearLayoutManagerEx
 * @author liulongchao
 * @since 2017/5/20
 */


class ExposeLinearLayoutManagerEx(context: Context?, orientation: Int, reverseLayout: Boolean) : LinearLayoutManager(context, orientation, reverseLayout) {

    private val TAG:String = "ExposeLLManagerEx"
    private val DEBUG:Boolean = false
    private val HORIZONTAL:Int = OrientationHelper.HORIZONTAL
    private val VERTICAL:Int = OrientationHelper.VERTICAL
    protected val INVALID_OFFSET:Int = Integer.MIN_VALUE

    private val MAX_SCROLL_FACTOR:Float = 0.33f

    /**
     * Helper class that keeps temporary layout state.
     * It does not keep state after layout is complete but we still keep a reference to re-use
     * the same object.
     */
    private var mLayoutState : LayoutState = null!!

    /**
     * Many calculations are made depending on orientation. To keep it clean, this interface
     * helps {@link LinearLayoutManager} make those decisions.
     * Based on {@link #mOrientation}, an implementation is lazily created in
     * {@link #ensureLayoutStateExpose} method.
     */
    private var mOrientationHelper : OrientationHelper = null!!
    /**
     * We need to track this so that we can ignore current position when it changes.
     */
    private var mLastStackFromEnd : Boolean
    /**
     * This keeps the final value for how LayoutManager should start laying out views.
     * It is calculated by checking {@link #getReverseLayout()} and View's layout direction.
     * {@link #onLayoutChildren(RecyclerView.Recycler, RecyclerView.State)} is run.
     */
    private var mShouldReverseLayoutExpose : Boolean = false
    /**
     * When LayoutManager needs to scroll to a position, it sets this variable and requests a
     * layout which will check this variable and re-layout accordingly.
     */
    private var mCurrentPendingScrollPosition:Int = RecyclerView.NO_POSITION
    /**
     * Used to keep the offset value when {@link #scrollToPositionWithOffset(int, int)} is
     * called.
     */
    private var mPendingScrollPositionOffset : Int = INVALID_OFFSET
    private var mCurrentPendingSavedState : Bundle = null!!
    /**
     * Re-used variable to keep anchor information on re-layout.
     * Anchor position and coordinate defines the reference point for LLM while doing a layout.
     */
    private var mAnchorInfo : AnchorInfo = null!!
    private var mChildHelperWrapper : ChildHelperWrapper = null!!
    private var mEnsureLayoutStateMethod : Method

    /**
     * Creates a vertical LinearLayoutManager
     *
     * @param context Current context, will be used to access resources.
     */
    constructor(context: Context?) : this(context, OrientationHelper.VERTICAL, false){
        mAnchorInfo = AnchorInfo()
        setOrientation(this.orientation)
        setReverseLayout(reverseLayout)
        mChildHelperWrapper = ChildHelperWrapper(this)
        try {
            mEnsureLayoutStateMethod = LinearLayoutManager::class.java.getDeclaredMethod("ensureLayoutState")
            mEnsureLayoutStateMethod.isAccessible = true
        } catch(e: Exception) {
        }


    }
    /**
     * @param context       Current context, will be used to access resources.
     * @param orientation   Layout orientation. Should be {@link #HORIZONTAL} or {@link
     *                      #VERTICAL}.
     * @param reverseLayout When set to true, layouts from end to start.
     */



    inner class ChildHelperWrapper constructor(layoutManager : RecyclerView.LayoutManager){

    }

    inner class LayoutState constructor(){

        var vhIsRemoved : Method = null!!
        val TAG:String = "_ExposeLLayoutManager#LayoutState"
        val LAYOUT_START:Int = 1
        val LAYOUT_END:Int = 1
        val INVALID_LAYOUT:Int = Int.MIN_VALUE

    }

    inner class AnchorInfo constructor(){
        var mPosition : Int = 0
        var mCoordinate : Int = 0
        var mLayoutFromEnd : Boolean = false

        fun reset(){
            mPosition = RecyclerView.NO_POSITION
            mCoordinate = INVALID_OFFSET
            mLayoutFromEnd = false
        }

        fun assignCoordinateFromPadding(){
            mCoordinate = if (mLayoutFromEnd)
                mOrientationHelper.getEndAfterPadding()
            else
                mOrientationHelper.getStartAfterPadding()
        }
    }
}