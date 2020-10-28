package com.example.llcgs.android_kotlin.ui.recyclerview.layoutmanager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * com.example.llcgs.android_kotlin.ui.recyclerview.layoutmanager.SlideCardLayoutManager
 *
 * @author liulongchao
 * @since 2020/10/27
 */
public class SlideCardLayoutManager extends RecyclerView.LayoutManager {


    /**
     * 获取布局参数
     *
     * @return LayoutParams
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 自定义 item 的布局方式
     *
     * @param recycler recycler
     * @param state    state
     */
    //布局
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        // ViewHolder 回收复用
        detachAndScrapAttachedViews(recycler);
        // 布局
        int itemCount = getItemCount();
        int bottomPosition;
        if (itemCount < CardConfig.MAX_SHOW_COUNT) {
            bottomPosition = 0;
        } else {
            bottomPosition = itemCount - CardConfig.MAX_SHOW_COUNT;
        }

        for (int i = bottomPosition; i < itemCount; i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            // 测量子view的大小
            measureChildWithMargins(view, 0, 0);
            // 宽度 会把ItemDecoration的分割线的宽度加进去
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            // 高度 会把ItemDecoration的分割线的高度加进去
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
            // 布局 使用这个方法布局，会把Inset考虑进去
            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2, widthSpace / 2 + getDecoratedMeasuredWidth(view), heightSpace / 2 + getDecoratedMeasuredHeight(view));
            // 向下平移，并进行了缩放
            int level = itemCount - i - 1;
            if (level > 0) {
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    // Y轴平移
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * level);
                    //
                    view.setScaleX(1 - CardConfig.SCALE_GAP * level);
                    view.setScaleY(1 - CardConfig.SCALE_GAP * level);
                } else {
                    // 最下面的那个 View 与前一个 View 布局一样
                    // Y轴平移
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * (level - 1));
                    // X轴缩放
                    view.setScaleX(1 - CardConfig.SCALE_GAP * (level - 1));
                    // Y轴缩放
                    view.setScaleY(1 - CardConfig.SCALE_GAP * (level - 1));
                }
            }

        }
    }
}
