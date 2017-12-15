/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.example.llcgs.android_kotlin.material.detail.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.llcgs.android_kotlin.R;

public class LoadMoreAdapter extends MergeAdapter {

    private LoadMoreViewAdapter mViewAdapter;

    public LoadMoreAdapter(int loadMoreLayoutRes, RecyclerView.Adapter<?>... adapters) {
        super(mergeAdapters(adapters, new LoadMoreViewAdapter(loadMoreLayoutRes)));

        adapters = getAdapters();
        mViewAdapter = (LoadMoreViewAdapter) adapters[adapters.length - 1];
        mViewAdapter.setParentAdapter(this);
    }

    private static RecyclerView.Adapter<?>[] mergeAdapters(RecyclerView.Adapter<?>[] adapters,
                                                           RecyclerView.Adapter<?> adapter) {
        RecyclerView.Adapter<?>[] mergedAdapters = new RecyclerView.Adapter<?>[adapters.length + 1];
        System.arraycopy(adapters, 0, mergedAdapters, 0, adapters.length);
        mergedAdapters[adapters.length] = adapter;
        return mergedAdapters;
    }

    public boolean isProgressVisible() {
        return mViewAdapter.isProgressVisible();
    }

    public void setProgressVisible(boolean progressVisible) {
        mViewAdapter.setProgressVisible(progressVisible);
    }

    static class LoadMoreViewAdapter extends RecyclerView.Adapter<LoadMoreViewAdapter.ViewHolder> {

        private int mLoadMoreLayoutRes;

        private boolean mShowingItem;

        private ViewHolder mViewHolder;
        private boolean mProgressVisible;

        public LoadMoreViewAdapter(int loadMoreLayoutResId) {

            mLoadMoreLayoutRes = loadMoreLayoutResId;

            setHasStableIds(true);
        }

        // We need to postpone this until the super call of our parent is completed.
        public void setParentAdapter(final LoadMoreAdapter parentAdapter) {
            setShowingItem(parentAdapter.getItemCount() > 0);
            parentAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    onChanged();
                }
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    onChanged();
                }
                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    onChanged();
                }
                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    onChanged();
                }
                @Override
                public void onChanged() {
                    // Don't show the progress item if our parent is empty - or else this can lead
                    // to incorrect scrolling position when items are added (before us).
                    setShowingItem(parentAdapter.getItemCount() > 0);
                }
            });
        }

        public void setShowingItem(boolean showingItem) {

            if (mShowingItem == showingItem) {
                return;
            }

            mShowingItem = showingItem;
            if (mShowingItem) {
                notifyItemInserted(0);
            } else {
                notifyItemRemoved(0);
            }
        }

        public boolean isProgressVisible() {
            return mProgressVisible;
        }

        public void setProgressVisible(boolean progressVisible) {

            if (mProgressVisible == progressVisible) {
                return;
            }

            mProgressVisible = progressVisible;
            if (mShowingItem) {
                if (mViewHolder != null) {
                    onBindViewHolder(mViewHolder, 0);
                } else {
                    notifyItemChanged(0);
                }
            }
        }

        @Override
        public int getItemCount() {
            return mShowingItem ? 1 : 0;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLoadMoreLayoutRes, parent));
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams staggeredGridLayoutParams =
                        (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                staggeredGridLayoutParams.setFullSpan(true);
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //ViewUtils.setVisibleOrInvisible(holder.progress, mProgressVisible);
            mViewHolder = holder;
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            mViewHolder = null;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            //@BindView(R.id.progress)
            public ProgressBar progress;

            public ViewHolder(View itemView) {
                super(itemView);
                progress = itemView.findViewById(R.id.progress);
                //ButterKnife.bind(this, itemView);
            }
        }
    }
}
