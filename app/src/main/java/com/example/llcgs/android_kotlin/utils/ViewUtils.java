/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.example.llcgs.android_kotlin.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.content.res.AppCompatResources;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;

import com.example.llcgs.android_kotlin.R;

public class ViewUtils {

    private ViewUtils() {}

    public static void fadeOut(final View view, int duration, final boolean gone,
                               final Runnable nextRunnable) {
        if (view.getVisibility() != View.VISIBLE || view.getAlpha() == 0) {
            // Cancel any starting animation.
            view.animate()
                    .alpha(0)
                    .setDuration(0)
                    .start();
            view.setVisibility(gone ? View.GONE : View.INVISIBLE);
            if (nextRunnable != null) {
                nextRunnable.run();
            }
            return;
        }
        view.animate()
                .alpha(0)
                .setDuration(duration)
                .setInterpolator(new FastOutLinearInInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    private boolean mCanceled = false;
                    @Override
                    public void onAnimationCancel(Animator animator) {
                        mCanceled = true;
                    }
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if (!mCanceled) {
                            view.setVisibility(gone ? View.GONE : View.INVISIBLE);
                            if (nextRunnable != null) {
                                nextRunnable.run();
                            }
                        }
                    }
                })
                .start();
    }

    public static void fadeOut(View view, int duration, boolean gone) {
        fadeOut(view, duration, gone, null);
    }

    public static void fadeOut(View view, boolean gone) {
        fadeOut(view, getShortAnimTime(view), gone);
    }

    public static void fadeOut(View view) {
        fadeOut(view, true);
    }

    public static void fadeIn(View view, int duration) {
        if (view.getVisibility() == View.VISIBLE && view.getAlpha() == 1) {
            // Cancel any starting animation.
            view.animate()
                    .alpha(1)
                    .setDuration(0)
                    .start();
            return;
        }
        view.setAlpha(0);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(1)
                .setDuration(duration)
                .setInterpolator(new FastOutSlowInInterpolator())
                // NOTE: We need to remove any previously set listener or Android will reuse it.
                .setListener(null)
                .start();
    }

    public static void fadeIn(View view) {
        fadeIn(view, getShortAnimTime(view));
    }

    public static void fadeToVisibility(View view, boolean visible, boolean gone) {
        if (visible) {
            fadeIn(view);
        } else {
            fadeOut(view, gone);
        }
    }

    public static void fadeToVisibility(View view, boolean visible) {
        fadeToVisibility(view, visible, true);
    }

    public static void crossfade(View fromView, View toView, int duration, boolean gone) {
        fadeOut(fromView, duration, gone);
        fadeIn(toView, duration);
    }

    public static void crossfade(View fromView, View toView, boolean gone) {
        crossfade(fromView, toView, getShortAnimTime(fromView), gone);
    }

    public static void crossfade(View fromView, View toView) {
        crossfade(fromView, toView, false);
    }

    public static void fadeOutThenFadeIn(final View fromView, final View toView, final int duration,
                                         final boolean gone) {
        fadeOut(fromView, duration, gone, new Runnable() {
            @Override
            public void run() {
                fadeIn(toView, duration);
            }
        });
    }

    public static void fadeOutThenFadeIn(View fromView, View toView, boolean gone) {
        fadeOutThenFadeIn(fromView, toView, getShortAnimTime(fromView), gone);
    }

    public static void fadeOutThenFadeIn(final View fromView, final View toView) {
        fadeOutThenFadeIn(fromView, toView, false);
    }

    public static float dpToPx(float dp, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    public static int dpToPxOffset(float dp, Context context) {
        return (int) dpToPx(dp, context);
    }

    public static int dpToPxSize(float dp, Context context) {
        float value = dpToPx(dp, context);
        int size = (int) (value + 0.5f);
        if (size != 0) {
            return size;
        } else if (value == 0) {
            return 0;
        } else if (value > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public static boolean getBooleanFromAttrRes(int attrRes, boolean defaultValue, Context context) {
        TypedArray a = context.obtainStyledAttributes(new int[] { attrRes });
        try {
            return a.getBoolean(0, defaultValue);
        } finally {
            a.recycle();
        }
    }

    public static int getColorFromAttrRes(int attrRes, int defaultValue, Context context) {
        TypedArray a = context.obtainStyledAttributes(new int[] { attrRes });
        try {
            return a.getColor(0, defaultValue);
        } finally {
            a.recycle();
        }
    }

    public static ColorStateList getColorStateListFromAttrRes(int attrRes, Context context) {
        // TODO: Switch to TintTypedArray when they added this overload.
        TypedArray a = context.obtainStyledAttributes(new int[] { attrRes });
        try {
            // 0 is an invalid identifier according to the docs of {@link Resources}.
            int resId = a.getResourceId(0, 0);
            if (resId != 0) {
                return AppCompatResources.getColorStateList(context, resId);
            }
            return null;
        } finally {
            a.recycle();
        }
    }

    public static Drawable getDrawableFromAttrRes(int attrRes, Context context) {
        // TODO: Switch to TintTypedArray when they added this overload.
        TypedArray a = context.obtainStyledAttributes(new int[] { attrRes });
        try {
            // 0 is an invalid identifier according to the docs of {@link Resources}.
            int resId = a.getResourceId(0, 0);
            if (resId != 0) {
                return AppCompatResources.getDrawable(context, resId);
            }
            return null;
        } finally {
            a.recycle();
        }
    }

    public static float getFloatFromAttrRes(int attrRes, float defaultValue, Context context) {
        TypedArray a = context.obtainStyledAttributes(new int[] { attrRes });
        try {
            return a.getFloat(0, defaultValue);
        } finally {
            a.recycle();
        }
    }

    public static int getResIdFromAttrRes(int attrRes, int defaultValue, Context context) {
        // TODO: Switch to TintTypedArray when they added this overload.
        TypedArray a = context.obtainStyledAttributes(new int[] { attrRes });
        try {
            return a.getResourceId(0, defaultValue);
        } finally {
            a.recycle();
        }
    }

    public static int getShortAnimTime(Resources resources) {
        return resources.getInteger(android.R.integer.config_shortAnimTime);
    }

    public static int getShortAnimTime(View view) {
        return getShortAnimTime(view.getResources());
    }

    public static int getShortAnimTime(Context context) {
        return getShortAnimTime(context.getResources());
    }

    public static int getMediumAnimTime(Resources resources) {
        return resources.getInteger(android.R.integer.config_mediumAnimTime);
    }

    public static int getMediumAnimTime(View view) {
        return getMediumAnimTime(view.getResources());
    }

    public static int getMediumAnimTime(Context context) {
        return getMediumAnimTime(context.getResources());
    }

    public static int getLongAnimTime(Resources resources) {
        return resources.getInteger(android.R.integer.config_longAnimTime);
    }

    public static int getLongAnimTime(View view) {
        return getLongAnimTime(view.getResources());
    }

    public static int getLongAnimTime(Context context) {
        return getLongAnimTime(context.getResources());
    }

    public static int getMarginStart(View view) {
        return MarginLayoutParamsCompat.getMarginStart(
                (ViewGroup.MarginLayoutParams) view.getLayoutParams());
    }

    public static int getMarginEnd(View view) {
        return MarginLayoutParamsCompat.getMarginEnd(
                (ViewGroup.MarginLayoutParams) view.getLayoutParams());
    }

    public static int getMarginLeft(View view) {
        return ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).leftMargin;
    }

    public static int getMarginRight(View view) {
        return ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).rightMargin;
    }

    public static int getMarginTop(View view) {
        return ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin;
    }

    public static int getMarginBottom(View view) {
        return ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin;
    }

    public static int getWidthExcludingPadding(View view) {
        return Math.max(0, view.getWidth() - view.getPaddingLeft() - view.getPaddingRight());
    }

    public static int getHeightExcludingPadding(View view) {
        return Math.max(0, view.getHeight() - view.getPaddingTop() - view.getPaddingBottom());
    }

    private static boolean hasSwDp(int dp, Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp >= dp;
    }

    public static boolean hasSw600Dp(Context context) {
        return hasSwDp(600, context);
    }

    private static boolean hasWDp(int dp, Context context) {
        return context.getResources().getConfiguration().screenWidthDp >= dp;
    }

    public static boolean hasW600Dp(Context context) {
        return hasWDp(600, context);
    }

    public static boolean hasW960Dp(Context context) {
        return hasWDp(960, context);
    }

    public static void hideTextInputLayoutErrorOnTextChange(EditText editText,
                                                            final TextInputLayout textInputLayout) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                textInputLayout.setError(null);
            }
        });
    }

    public static View inflate(int resource, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
    }

    public static View inflateInto(int resource, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(resource, parent);
    }

    public static boolean isLightTheme(Context context) {
        return getBooleanFromAttrRes(R.attr.isLightTheme, false, context);
    }

    public static boolean isInLandscape(Context context) {
        return context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    public static void postOnDrawerClosed(final DrawerLayout drawerLayout,
                                          final Runnable runnable) {
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayout.removeDrawerListener(this);
                runnable.run();
            }
        });
    }

    public static void postOnPreDraw(final View view, final Runnable runnable) {
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                runnable.run();
                return true;
            }
        });
    }

    public static float pxToDp(float px, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / metrics.density;
    }

    public static int pxToDpInt(float px, Context context) {
        return Math.round(pxToDp(px, context));
    }

    public static void replaceChild(ViewGroup viewGroup, View oldChild, View newChild) {
        int index = viewGroup.indexOfChild(oldChild);
        viewGroup.removeViewAt(index);
        viewGroup.addView(newChild, index);
    }

    public static void setBackgroundPreservingPadding(View view, Drawable background) {
        int savedPaddingStart = ViewCompat.getPaddingStart(view);
        int savedPaddingEnd = ViewCompat.getPaddingEnd(view);
        int savedPaddingTop = view.getPaddingTop();
        int savedPaddingBottom = view.getPaddingBottom();
        ViewCompat.setBackground(view, background);
        ViewCompat.setPaddingRelative(view, savedPaddingStart, savedPaddingTop, savedPaddingEnd,
                savedPaddingBottom);
    }

    public static void setMarginStart(View view, int marginStart) {
        MarginLayoutParamsCompat.setMarginStart(
                (ViewGroup.MarginLayoutParams) view.getLayoutParams(), marginStart);
    }

    public static void setMarginEnd(View view, int marginEnd) {
        MarginLayoutParamsCompat.setMarginEnd((ViewGroup.MarginLayoutParams) view.getLayoutParams(),
                marginEnd);
    }

    public static void setMarginLeft(View view, int marginLeft) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).leftMargin = marginLeft;
    }

    public static void setMarginRight(View view, int marginRight) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).rightMargin = marginRight;
    }

    public static void setMarginTop(View view, int marginTop) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin = marginTop;
    }

    public static void setMarginBottom(View view, int marginBottom) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = marginBottom;
    }

    public static void setWidth(View view, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams.width == width) {
            return;
        }
        layoutParams.width = width;
        view.setLayoutParams(layoutParams);
    }

    public static void setHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams.height == height) {
            return;
        }
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    public static void setSize(View view, int size) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams.width == size && layoutParams.height == size) {
            return;
        }
        layoutParams.width = size;
        layoutParams.height = size;
        view.setLayoutParams(layoutParams);
    }

    public static void setLayoutFullscreen(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    public static void setLayoutFullscreen(Activity activity) {
        setLayoutFullscreen(activity.getWindow().getDecorView());
    }

    public static void setTextViewBold(TextView textView, boolean bold) {

        Typeface typeface = textView.getTypeface();
        if (typeface.isBold() == bold) {
            return;
        }

        int style = textView.getTypeface().getStyle();
        if (bold) {
            style |= Typeface.BOLD;
        } else {
            style &= ~Typeface.BOLD;
        }
        // Workaround insane behavior in TextView#setTypeface(Typeface, int).
        if (style > 0) {
            textView.setTypeface(typeface, style);
        } else {
            textView.setTypeface(Typeface.create(typeface, style), style);
        }
    }

    public static void setTextViewItalic(TextView textView, boolean italic) {

        Typeface typeface = textView.getTypeface();
        if (typeface.isItalic() == italic) {
            return;
        }

        int style = textView.getTypeface().getStyle();
        if (italic) {
            style |= Typeface.ITALIC;
        } else {
            style &= ~Typeface.ITALIC;
        }
        // Workaround insane behavior in TextView#setTypeface(Typeface, int).
        if (style > 0) {
            textView.setTypeface(typeface, style);
        } else {
            textView.setTypeface(Typeface.create(typeface, style), style);
        }
    }

    public static void setTextViewLinkClickable(TextView textView) {
        textView.setMovementMethod(ClickableMovementMethod.getInstance());
        // Reset for TextView.fixFocusableAndClickableSettings(). We don't want View.onTouchEvent()
        // to consume touch events.
        textView.setClickable(false);
        textView.setLongClickable(false);
    }

    public static void setVisibleOrGone(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public static void setVisibleOrInvisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }
}
