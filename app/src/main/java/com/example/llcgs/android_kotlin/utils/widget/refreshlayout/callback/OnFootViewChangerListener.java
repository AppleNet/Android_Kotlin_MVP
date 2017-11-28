package com.example.llcgs.android_kotlin.utils.widget.refreshlayout.callback;

/**
 * 作者：Alex
 * 时间：2016年11月22日
 * 简述：
 */

public abstract class OnFootViewChangerListener {
    /**
     * 加载更多，没有响应上拉加载
     */
    public abstract void onLoadMoreNone();

    /**
     * 加载更多，到了临界状态
     */
    public abstract void onLoadMoreCritical();

    /**
     * 加载更多，开始
     */
    public abstract void onLoadMoreStart();

    /**
     * 加载更多，结束
     */
    public abstract void onLoadMoreFinish();
}
