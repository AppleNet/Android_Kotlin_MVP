package com.example.llcgs.android_kotlin.ui.viewpager.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;


public class NonPageTransformer implements ViewPager.PageTransformer {

    public static final ViewPager.PageTransformer INSTANCE = new NonPageTransformer();

    @Override
    public void transformPage(@NonNull View page, float position) {
        page.setScaleX(0.999f); //hack
    }


}
