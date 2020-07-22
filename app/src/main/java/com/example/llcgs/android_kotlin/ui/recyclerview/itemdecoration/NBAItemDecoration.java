package com.example.llcgs.android_kotlin.ui.recyclerview.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.llcgs.android_kotlin.customview.utils.Utils;
import com.example.llcgs.android_kotlin.ui.recyclerview.adapter.RecyclerViewAdapter;

public class NBAItemDecoration extends RecyclerView.ItemDecoration {

    private int groupHeaderHeight;
    private Paint headerPaint;
    private Paint textPaint;
    private Paint itemPaint;
    private Rect textRect;

    public NBAItemDecoration(Context context) {
        groupHeaderHeight = dip2px(context, 50);

        headerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        headerPaint.setColor(Color.RED);

        itemPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        itemPaint.setColor(Color.BLACK);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(dip2px(context, 15));

        textRect = new Rect();
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        // 绘制分组标签
        if (parent.getAdapter() instanceof RecyclerViewAdapter) {
            RecyclerViewAdapter adapter = (RecyclerViewAdapter) parent.getAdapter();
            // 获取屏幕上可见的 item 数量
            int childCount = parent.getChildCount();
            // 获取 left 边距
            int left = parent.getPaddingLeft();
            // 获取 right 边距
            int right = parent.getWidth() - parent.getPaddingRight();
            // 遍历屏幕上所有可见的 item
            for (int i = 0; i < childCount; i++) {
                // 获取对应 i 的 view
                View itemView = parent.getChildAt(i);
                // 根据获取的对应的 view 拿到对应的位置
                int position = parent.getChildLayoutPosition(itemView);
                boolean isGroupHeader = adapter.isGroupHeader(position);
                if (isGroupHeader && itemView.getTop() - groupHeaderHeight - parent.getPaddingTop() >= 0) {
                    // 绘制头部区域
                    canvas.drawRect(left, itemView.getTop() - groupHeaderHeight, right, itemView.getTop(), headerPaint);
                    // 绘制文本
                    String groupName = adapter.getGroupName(position);
                    textPaint.getTextBounds(groupName,0, groupName.length(), textRect);
                    canvas.drawText(groupName, left + Utils.dp2px(10), itemView.getTop() - groupHeaderHeight / 2 + textRect.height() / 2, textPaint);
                } else if (itemView.getTop() - groupHeaderHeight - parent.getPaddingTop() >= 0) {
                    // 绘制分割线
                    canvas.drawRect(left, itemView.getTop() - 1, right, itemView.getTop(), itemPaint);
                }
            }
        }
    }

    /**
     *  绘制在最顶层，即绘制在 item 之上
     *
     * */
    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        // 绘制吸顶效果
        if (parent.getAdapter() instanceof RecyclerViewAdapter) {
            RecyclerViewAdapter adapter = (RecyclerViewAdapter) parent.getAdapter();
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            int position = layoutManager.findFirstVisibleItemPosition();
            View itemView = (parent.findViewHolderForAdapterPosition(position)).itemView;
            // 获取 left 边距
            int left = parent.getPaddingLeft();
            // 获取 right 边距
            int right = parent.getWidth() - parent.getPaddingRight();
            int top = parent.getPaddingTop();
            // 计算 bottom
            // 当第二个是组的头部的时候
            boolean isGroupHeader = adapter.isGroupHeader(position + 1);
            if (isGroupHeader) {
                //
                int bottom = Math.min(groupHeaderHeight, itemView.getBottom() - top);
                // 绘制吸顶区域
                canvas.drawRect(left, top, right, top + bottom, headerPaint);
                // 绘制文本
                String groupName = adapter.getGroupName(position);
                textPaint.getTextBounds(groupName,0, groupName.length(), textRect);
                if ((top + bottom - groupHeaderHeight / 2) - parent.getPaddingTop() >= 0) {
                    canvas.drawText(groupName,
                            left + Utils.dp2px(10),
                            top + bottom - groupHeaderHeight / 2 + textRect.height() / 2,
                            textPaint);
                }
            } else {
                // 绘制吸顶区域
                canvas.drawRect(left, top, right, top + groupHeaderHeight, headerPaint);
                // 绘制文本
                String groupName = adapter.getGroupName(position);
                textPaint.getTextBounds(groupName,0, groupName.length(), textRect);
                canvas.drawText(groupName, left + Utils.dp2px(10), top + groupHeaderHeight / 2 + textRect.height() / 2, textPaint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getAdapter() instanceof RecyclerViewAdapter) {
            RecyclerViewAdapter adapter = (RecyclerViewAdapter) parent.getAdapter();
            // int position = parent.getLayoutManager().getPosition(view);
            int position = parent.getChildLayoutPosition(view);
            boolean isGroupHeader = adapter.isGroupHeader(position);
            if (isGroupHeader) {
                // 如果是头部，预留更大空间
                outRect.set(0, groupHeaderHeight, 0, 0);
            } else {
                // 空出1像素
                outRect.set(0, 1,0, 0);
            }
        }
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
