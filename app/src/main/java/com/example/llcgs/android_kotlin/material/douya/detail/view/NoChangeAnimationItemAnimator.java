/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.example.llcgs.android_kotlin.material.douya.detail.view;

import android.support.v7.widget.DefaultItemAnimator;

/**
 * A DefaultItemAnimator with setSupportsChangeAnimations(false).
 */
public class NoChangeAnimationItemAnimator extends DefaultItemAnimator {

    public NoChangeAnimationItemAnimator() {
        setSupportsChangeAnimations(false);
    }
}
