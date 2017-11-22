package com.example.llcgs.android_kotlin.mvvm.attributesetters.widget.refreshlayout.callback;

/**
 * 作者：Alex
 * 时间：2016年11月21日
 * 简述：
 */
public interface OnRefreshListener {
    /**
     * 开始 下拉
     */
    void onRefresh();

    /**
     * 开始 加载更多
     */
    void onLoadMore();
}