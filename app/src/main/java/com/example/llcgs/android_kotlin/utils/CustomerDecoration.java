package com.example.llcgs.android_kotlin.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * com.gomejr.myf.widget.itemdecoration.CustomerDecoration
 *
 * @author liulongchao
 * @since 2016/12/23
 */

public class CustomerDecoration extends RecyclerView.ItemDecoration {

    private Builder builder;

    private CustomerDecoration(Builder builder){
        this.builder = builder;
        builder.mDivider = new GradientDrawable();
        builder.mDivider.setColor(builder.mDividerColor);
    }

    public void attachRecyclerView(RecyclerView recyclerView){
        recyclerView.addItemDecoration(this);
    }

    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, builder.mTop, 0, builder.mDividerHeight);
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (builder.mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + builder.mDividerHeight;
            if (builder.mDivider != null) {
                builder.mDivider.setBounds(left, top, right, bottom);
                builder.mDivider.draw(canvas);
            }
            if (builder.mPaint != null) {
                canvas.drawRect(left, top, right, bottom, builder.mPaint);
            }
        }
    }

    //绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + builder.mDividerHeight;
            if (builder.mDivider != null) {
                builder.mDivider.setBounds(left, top, right, bottom);
                builder.mDivider.draw(canvas);
            }
            if (builder.mPaint != null) {
                canvas.drawRect(left, top, right, bottom, builder.mPaint);
            }
        }
    }


    public static class Builder{

        private int mOrientation = LinearLayout.HORIZONTAL;
        private int mTop = 0;
        private int mLeft = 0;
        private int mRight = 0;
        private int mDividerHeight = 20;
        private int mDividerColor = 0;

        private GradientDrawable mDivider;
        private Paint mPaint;
        private CustomerDecoration customerDecoration;

        private static class BuilderHolder{
            private static Builder instance = new Builder();
        }

        private Builder(){
            customerDecoration = new CustomerDecoration(this);
        }

        public static Builder getInstance(){
            return BuilderHolder.instance;
        }

        public Builder orientation(int orientation){
            if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
                throw new IllegalArgumentException("请输入正确的参数！");
            }
            this.mOrientation = orientation;
            return this;
        }

        public Builder left(int left){
            this.mLeft = left;
            return this;
        }

        public Builder top(int top){
            this.mTop = top;
            return this;
        }

        public Builder right(int right){
            this.mRight = right;
            return this;
        }

        public Builder bottom(int dividerHeight){
            this.mDividerHeight = dividerHeight;
            return this;
        }

        public Builder divider(Drawable divider){
            this.mDivider = (GradientDrawable) divider;
            return this;
        }

        public Builder color(int dividerColor){
            this.mDividerColor = dividerColor;
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(mDividerColor);
            mPaint.setStyle(Paint.Style.FILL);
            return this;
        }

        public CustomerDecoration build(){
            return customerDecoration;
        }

    }


}
