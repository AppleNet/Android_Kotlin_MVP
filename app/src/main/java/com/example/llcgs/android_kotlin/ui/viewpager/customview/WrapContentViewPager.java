package com.example.llcgs.android_kotlin.ui.viewpager.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 *  com.example.llcgs.android_kotlin.ui.viewpager.customview.WrapContentViewPager
 *
 * @author liulongchao
 * @since  2020-07-27
 *
 *  解决ViewPager设置wrap_content 无效的case
 * */
public class WrapContentViewPager extends ViewPager {
    public WrapContentViewPager(@NonNull Context context) {
        super(context);
    }

    public WrapContentViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMeasureSpec, hMeasureSpec;
        int count  = getChildCount();
        View child;

        int finalH = 0;
        int finalW = 0;

        for (int i = 0; i < count; i++) {
            child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childW = child.getMeasuredWidth();
            int childH = child.getMeasuredHeight();

            finalH = finalH > childH ? finalH : childH;
            finalW = finalW > childW ? finalW : childW;
        }

        wMeasureSpec = MeasureSpec.makeMeasureSpec(finalW, MeasureSpec.EXACTLY);
        hMeasureSpec = MeasureSpec.makeMeasureSpec(finalH, MeasureSpec.EXACTLY);

        super.onMeasure(wMeasureSpec, hMeasureSpec);
    }
}
