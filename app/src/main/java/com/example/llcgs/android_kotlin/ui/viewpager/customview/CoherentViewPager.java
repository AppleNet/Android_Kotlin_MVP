package com.example.llcgs.android_kotlin.ui.viewpager.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CoherentViewPager extends ViewPager {

    /**
     * 触摸时按下的点
     **/
    private PointF downP = new PointF();
    /**
     * 触摸时当前的点
     **/
    private PointF curP = new PointF();

    private float oldx = 0.0f;
    private float first = 0.0f;
    private OnSingleTouchListener onSingleTouchListener;

    public CoherentViewPager(@NonNull Context context) {
        super(context);
    }

    public CoherentViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 拦截触摸事件，说明在此处处理onTouchEvent事件，子View先处理滑动事件
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //每次进行onTouch事件都记录当前的按下的坐标
        curP.x = ev.getX();
        curP.y = ev.getY();
        int event = ev.getAction();
        switch (event) {
            case MotionEvent.ACTION_DOWN:
                downP.x = ev.getX();
                downP.y = ev.getY();
                oldx = downP.x;
                first = 0.0f;
                // 拦截 告诉父容器，在这里处理按下事件
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_MOVE:
                float newX = ev.getX();
                int curPosition = getCurrentItem();
                int count = this.getAdapter() != null ? getAdapter().getCount() - 1 : 0;
                // 预测本次滑动的的方向（first>0 说明本次手势向右滑动）
                if (first == 0.0f) {
                    //第一次进来
                    if (Math.abs(newX - oldx) > 0) {
                        first = newX - oldx;
                    }
                }
                // 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
                // 当子viewpager的position处于0时,检测如果是向右滑动说明要通知父ViewPager滑动;
                // 当子viewpager的position处于size-1时,检测如果是向左滑动说明要通知父ViewPager滑动;
                // (newX-oldX)实时检测滑动方向
                if ((curPosition == count && (newX - oldx) < 0 && first < 0) || (curPosition == 0 && (newX - oldx) > 0 && first > 0)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                oldx = newX;
                break;
            case MotionEvent.ACTION_UP:
                //在up时判断是否按下和松手的坐标为一个点
                //如果是一个点，将执行点击事件，这是我自己写的点击事件，而不是onclick
                if(downP.x==curP.x && downP.y==curP.y){
                    onSingleTouch();
                    return true;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 单击
     */
    public void onSingleTouch() {
        if (onSingleTouchListener!= null) {
            onSingleTouchListener.onSingleTouch();
        }
    }

    /**
     * 创建点击事件接口
     *
     * @author wanpg
     */
    public interface OnSingleTouchListener {
        public void onSingleTouch();
    }

    public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }

}

