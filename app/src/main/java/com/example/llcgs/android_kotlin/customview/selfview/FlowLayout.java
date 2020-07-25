package com.example.llcgs.android_kotlin.customview.selfview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *  获取到包含 margin 值的 layoutParams
     *
     *  重写了这个方法之后，子控件在getLayoutParams的时候，就能获取到 MarginLayoutParams
     * */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获取到父容器，允许当前控件的大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 获取到自己的宽高模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        // 测量子控件，根据子控件的大小，来决定自己的大小

        // 保存当前控件的里面的子控件的总宽高
        int childCountWidth = 0;
        int childCountHeight = 0;

        // 当前控件中子控件一行使用的宽度值
        int lineCountWidth = 0;
        // 保存一行中最高的子控件的高度
        int lineCountHeight = 0;

        // 存储每个子控件的宽高
        int iChildWidth  = 0;
        int iChildHeight = 0;

        int childCount = getChildCount();
        // 遍历所有的子控件
        for (int i = 0; i < childCount; i++) {
            // 获取到每一个子控件
            View childAt = getChildAt(i);
            // 测量每个子控件
            measureChildWithMargins(childAt, widthMeasureSpec, 0, heightMeasureSpec, 0);
            // 获取每个子控件的 layoutParams
            // 考虑到每个子控件的 padding margin 值，需要重写容器的generateLayoutParams 这个接口，然你 new 一个 MarginLayoutParams
            MarginLayoutParams params = (MarginLayoutParams) childAt.getLayoutParams();
            // 获取到当前子控件要占用的宽度
            iChildWidth = childAt.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            // 获取到当前子控件要占用的高度
            iChildHeight = childAt.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            // 判断当前子控件需不需要换行
            if (iChildWidth + lineCountWidth > widthSize) {

            }


        }

        // 第一种情况，当前控件宽高 设置的是 match_parent 就可以直接用它父亲的值
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
