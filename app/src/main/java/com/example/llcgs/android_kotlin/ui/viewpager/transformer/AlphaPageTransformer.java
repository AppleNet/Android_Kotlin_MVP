package com.example.llcgs.android_kotlin.ui.viewpager.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class AlphaPageTransformer extends BasePageTransformer {

    private static final float DEFAULT_MIN_ALPHA = 0.5f;
    private float mMinAlpha = DEFAULT_MIN_ALPHA;

    public AlphaPageTransformer() {
    }

    public AlphaPageTransformer(float minAlpha) {
        this(minAlpha, NonPageTransformer.INSTANCE);
    }

    public AlphaPageTransformer(ViewPager.PageTransformer pageTransformer) {
        this(DEFAULT_MIN_ALPHA, pageTransformer);
    }

    public AlphaPageTransformer(float minAlpha, ViewPager.PageTransformer pageTransformer) {
        mMinAlpha = minAlpha;
        mPageTransformer = pageTransformer;
    }

    @Override
    protected void pageTransform(View view, float position) {
        view.setScaleX(0.999f);

        if (position < -1) {
            // [-Infinity,-1)
            view.setAlpha(mMinAlpha);
        } else if (position <= 1) {
            // [-1, 1]
            if (position < 0) {
                // [0, -1]
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                view.setAlpha(factor);
            } else {
                // [1，0]
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                view.setAlpha(factor);
            }
        } else {
            // (1,+Infinity]
            view.setAlpha(mMinAlpha);
        }
    }
}
