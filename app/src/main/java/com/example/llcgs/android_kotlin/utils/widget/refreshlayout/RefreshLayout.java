package com.example.llcgs.android_kotlin.utils.widget.refreshlayout;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.TextView;

import com.example.llcgs.android_kotlin.utils.widget.refreshlayout.callback.OnFootViewChangerListener;
import com.example.llcgs.android_kotlin.utils.widget.refreshlayout.callback.OnRefreshListener;
import com.example.llcgs.android_kotlin.utils.widget.refreshlayout.view.CircleLoadingView;
import com.example.llcgs.android_kotlin.utils.widget.refreshlayout.view.MaterialProgressDrawable;

@SuppressWarnings("all")
public class RefreshLayout extends ViewGroup implements NestedScrollingParent, NestedScrollingChild {
    public static final int LARGE = MaterialProgressDrawable.LARGE;
    public static final int DEFAULT = MaterialProgressDrawable.DEFAULT;
    @VisibleForTesting
    /**
     * 圆环的直径
     */ protected static final int CIRCLE_DIAMETER = 40;
    @VisibleForTesting
    /**
     * 圆环的外层圈
     */ protected static final int CIRCLE_DIAMETER_LARGE = 56;
    protected static final String LOG_TAG = RefreshLayout.class.getSimpleName();

    /**
     * 完全 不透明，完全 可见
     */
    protected static final int MAX_ALPHA = 255;
    protected static final int STARTING_PROGRESS_ALPHA = (int) (.3f * MAX_ALPHA);

    protected static final float DECELERATE_INTERPOLATION_FACTOR = 2f;
    protected static final int INVALID_POINTER = -1;
    protected static final float DRAG_RATE = .5f;

    // Max amount of circle that can be filled by progress during swipe gesture,
    // where 1.0 is a full circle
    protected static final float MAX_PROGRESS_ANGLE = .8f;

    protected static final int SCALE_DOWN_DURATION = 150;

    protected static final int ALPHA_ANIMATION_DURATION = 300;

    protected static final int ANIMATE_TO_TRIGGER_DURATION = 200;

    protected static final int ANIMATE_TO_START_DURATION = 200;

    // Default background for the progress spinner
    protected static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    // Default offset in dips from the top of the view to where the progress spinner should stop
    protected static final int DEFAULT_CIRCLE_TARGET = 64;
    /**
     * xml 文件中，SwipeRefreshLayout 的直接子 布局
     */
    protected View targetView;
    protected OnRefreshListener onRefreshListener;
    /**
     * 正在 执行 刷新中
     */
    protected boolean refreshing = false;
    /**
     * 可以加载更多 设置为false之后，滑动到底部也不会响应loadMore
     */
    protected boolean loadMoreEnable = false;
    protected boolean refreshEnable;
    /**
     * 正在 执行 加载更多中
     */
    protected boolean isLoadingMore;
    /**
     * 加载更多， 操作中 取消了 上拉加载
     */
    protected boolean isLoadingMoreCancel;
    protected int touchSlop;
    /**
     * 触发 下拉刷新的 回调的触发距离
     */
    protected float refreshMaxDragDistance = -1;
    /**
     * 上拉加载的最小滑动距离
     */
    protected int loadMoreMinDistance;
    /**
     * 向下拉，的  滑动距离
     */
    protected float swipeDownUnconsumed;
    /**
     * 向上拉，的  滑动距离
     */
    protected float swipeUpUnconsumed;
    protected final NestedScrollingParentHelper nestedScrollingParentHelper;
    protected final NestedScrollingChildHelper nestedScrollingChildHelper;
    protected final int[] mParentScrollConsumed = new int[2];
    protected final int[] parentOffsetInWindow = new int[2];
    protected boolean nestedScrollInProgress;

    protected int mMediumAnimationDuration;
    /**
     * CircleView 从顶部向下滑动，CircleView的上边距
     */
    protected int currentTargetOffsetTop;

    protected float mInitialMotionY;
    protected float mInitialDownY;
    protected boolean isBeingDragged;
    protected int mActivePointerId = INVALID_POINTER;
    // Whether this item is scaled up rather than clipped
    protected boolean scale;

    // Target is returning to its start offset because it was cancelled or a
    // refresh was triggered.
    protected boolean mReturningToStart;
    protected final DecelerateInterpolator decelerateInterpolator;
    protected CircleLoadingView circleView;
    protected int mCircleViewIndex = -1;

    protected int headFrom;

    protected float startingScale;

    protected int originalOffsetTop;

    protected int spinnerOffsetEnd;

    protected MaterialProgressDrawable progress;

    /**
     * 缩放动画
     */
    protected Animation scaleAnimation;

    protected Animation scaleDownAnimation;

    protected Animation alphaStartAnimation;

    protected Animation alphaMaxAnimation;

    protected Animation scaleDownToStartAnimation;

    protected boolean notify;

    /**
     * 圆圈 半径
     */
    protected int circleDiameter;

    // Whether the client has set a custom starting position;
    protected boolean usingCustomStart;

    protected OnChildScrollUpCallback childScrollUpCallback;
    protected OnChildScrollDownCallback childScrollDownCallback;

    private int width;
    private int height;
    /**
     * 手指从 底 向上滑动， 底部loading视图会跟着滑动， 但是offset会有一个最大值
     */
    private int footOffsetMax;
    /**
     * 手指从 顶部 向下滑动， 定部loading视图会跟着滑动， 但是offset会有一个最大值
     */
    private int headOffsetMax;
    protected OnFootViewChangerListener onFootViewChangerListener;
    /**
     * 下拉刷新   动画监听器
     */
    private AnimationListener refreshAnimatorListener = new SimpleAnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {

            if (isRefreshing()) {
                // Make sure the progress view is fully VISIBLE
                progress.setAlpha(MAX_ALPHA);
                progress.start();
                if (notify) {
                    onRefreshStart();
                }
                /*circleView 最终的 top 边距，此时CircleView 固定在 顶部，一直在转圈*/
                currentTargetOffsetTop = circleView.getTop();
            } else {
                reset();
            }
        }
    };


    protected void reset() {
        circleView.clearAnimation();
        progress.stop();
        circleView.setVisibility(View.GONE);
        setColorViewAlpha(MAX_ALPHA);
        // Return the circle to its start position
        if (scale) {
            setAnimationProgress(0 /* animation complete and view is hidden */);
        } else {
            /* requires update */
            setTargetOffsetTopAndBottom(originalOffsetTop - currentTargetOffsetTop, true);
        }
        currentTargetOffsetTop = circleView.getTop();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reset();
    }

    private void setColorViewAlpha(int targetAlpha) {
        circleView.getBackground().setAlpha(targetAlpha);
        progress.setAlpha(targetAlpha);
    }

    /**
     * The refresh indicator starting and resting position is always positioned
     * near the top of the refreshing content. This position is a consistent
     * location, but can be adjusted in either direction based on whether or not
     * there is a toolbar or actionbar present.
     * <p>
     * <strong>Note:</strong> Calling this will reset the position of the refresh indicator to
     * <code>start</code>.
     * </p>
     *
     * @param scale Set to true if there is no view at a higher z-order than where the progress
     *              spinner is set to appear. Setting it to true will cause indicator to be scaled
     *              up rather than clipped.
     * @param start The offset in pixels from the top of this view at which the
     *              progress spinner should appear.
     * @param end   The offset in pixels from the top of this view at which the
     *              progress spinner should come to rest after a successful swipe
     *              gesture.
     */
    public void setProgressViewOffset(boolean scale, int start, int end) {
        this.scale = scale;
        originalOffsetTop = start;
        spinnerOffsetEnd = end;
        usingCustomStart = true;
        reset();
        refreshing = false;
    }

    /**
     * @return The offset in pixels from the top of this view at which the progress spinner should
     * appear.
     */
    public int getProgressViewStartOffset() {
        return originalOffsetTop;
    }

    /**
     * @return The offset in pixels from the top of this view at which the progress spinner should
     * come to rest after a successful swipe gesture.
     */
    public int getProgressViewEndOffset() {
        return spinnerOffsetEnd;
    }

    /**
     * The refresh indicator resting position is always positioned near the top
     * of the refreshing content. This position is a consistent location, but
     * can be adjusted in either direction based on whether or not there is a
     * toolbar or actionbar present.
     *
     * @param scale Set to true if there is no view at a higher z-order than where the progress
     *              spinner is set to appear. Setting it to true will cause indicator to be scaled
     *              up rather than clipped.
     * @param end   The offset in pixels from the top of this view at which the
     *              progress spinner should come to rest after a successful swipe
     *              gesture.
     */
    public void setProgressViewEndTarget(boolean scale, int end) {
        spinnerOffsetEnd = end;
        this.scale = scale;
        circleView.invalidate();
    }

    /**
     * One of DEFAULT, or LARGE.
     */
    public void setSize(int size) {
        if (size != MaterialProgressDrawable.LARGE && size != MaterialProgressDrawable.DEFAULT) {
            return;
        }
        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        if (size == MaterialProgressDrawable.LARGE) {
            circleDiameter = (int) (CIRCLE_DIAMETER_LARGE * metrics.density);
        } else {
            circleDiameter = (int) (CIRCLE_DIAMETER * metrics.density);
        }
        // force the bounds of the progress circle inside the circle view to
        // update by setting it to null before updating its size and then
        // re-setting it
        circleView.setImageDrawable(null);
        progress.updateSizes(size);
        circleView.setImageDrawable(progress);
    }

    /**
     * Simple constructor to use when creating a SwipeRefreshLayout from code.
     *
     * @param context
     */
    public RefreshLayout(Context context) {
        this(context, null);
    }

    /**
     * Constructor that is called when inflating SwipeRefreshLayout from XML.
     *
     * @param context
     * @param attrs
     */
    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        isLoadingMore = false;
        loadMoreEnable = true;
        refreshEnable = true;
        isSmallGap = true;
        loadMoreMinDistance = (int) dp2px(72);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMediumAnimationDuration = getResources().getInteger(android.R.integer.config_mediumAnimTime);
        setWillNotDraw(false);
        decelerateInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);

        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        circleDiameter = (int) (CIRCLE_DIAMETER * metrics.density);

        createProgressView();
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
        // the absolute offset has to take into account that the circle starts at an offset
        spinnerOffsetEnd = (int) (DEFAULT_CIRCLE_TARGET * metrics.density);
        refreshMaxDragDistance = spinnerOffsetEnd;
        nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        nestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);

        originalOffsetTop = currentTargetOffsetTop = -circleDiameter;
        //LogUtil.e("originalOffsetTop = " + originalOffsetTop);
        moveToStart(1.0f);
        setColorSchemeColors(Color.parseColor("#ffff4444"), Color.parseColor("#ff33b5e5"), Color.parseColor("#ffff4444"), Color.parseColor("#ffffbb33"), Color.parseColor("#ff99cc00"));
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (mCircleViewIndex < 0) {
            return i;
        } else if (i == childCount - 1) {
            // Draw the selected child last
            return mCircleViewIndex;
        } else if (i >= mCircleViewIndex) {
            // Move the children after the selected child earlier one
            return i + 1;
        } else {
            // Keep the children before the selected child the same
            return i;
        }
    }

    private void createProgressView() {
        circleView = new CircleLoadingView(getContext(), CIRCLE_BG_LIGHT);
        progress = new MaterialProgressDrawable(getContext(), this);
        progress.setBackgroundColor(CIRCLE_BG_LIGHT);
        circleView.setImageDrawable(progress);
        circleView.setVisibility(View.GONE);
        //circleView.setVisibility(View.VISIBLE);
        addView(circleView);
    }

    /**
     * Set the listener to be notified when a refresh is triggered via the swipe
     * gesture.
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        onRefreshListener = listener;
    }

    /**
     * Pre API 11, alpha is used to make the progress circle appear instead of scale.
     */
    private boolean isAlphaUsedForScale() {
        return android.os.Build.VERSION.SDK_INT < 11;
    }

    /**
     * Notify the widget that refresh state has changed. Do not call this when
     * refresh is triggered by a swipe gesture.
     *
     * @param refreshing Whether or not the view should show refresh progress.
     */
    private void setRefreshing(boolean refreshing) {
        if (refreshing && this.refreshing != refreshing) {
            this.refreshing = refreshing;
        } else {
            setRefreshing(refreshing, false);
        }
    }

    private void startScaleUpAnimation(AnimationListener listener) {
        circleView.setVisibility(View.VISIBLE);
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            // Pre API 11, alpha is used in place of scale up to show the
            // progress circle appearing.
            // Don't adjust the alpha during appearance otherwise.
            progress.setAlpha(MAX_ALPHA);
        }
        scaleAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                setAnimationProgress(interpolatedTime);
            }
        };
        postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, mMediumAnimationDuration);
        scaleAnimation.setDuration(mMediumAnimationDuration);
        if (listener != null) {
            circleView.setAnimationListener(listener);
        }
        circleView.clearAnimation();
        circleView.startAnimation(scaleAnimation);
    }

    /**
     * Pre API 11, this does an alpha animation.
     *
     * @param progress
     */
    void setAnimationProgress(float progress) {
        if (isAlphaUsedForScale()) {
            setColorViewAlpha((int) (progress * MAX_ALPHA));
        } else {
            ViewCompat.setScaleX(circleView, progress);
            ViewCompat.setScaleY(circleView, progress);
        }
    }

    public View getCircleView() {
        return circleView;
    }

    /**
     * 设置下拉刷新
     *
     * @param refreshing true 回调下拉刷新
     * @param notify     展示下拉刷新动画
     */
    private void setRefreshing(boolean refreshing, final boolean notify) {
        if (this.refreshing != refreshing) {
            this.notify = notify;
            ensureTarget();
            if (refreshing) {
                this.refreshing = refreshing;
                headOffsetToCorrectPositionAnimate(currentTargetOffsetTop, refreshAnimatorListener);
            } else {
                startScaleDownAnimation(refreshAnimatorListener, refreshing);
            }
        }
    }

    /**
     * CircleView 可见，在 顶部展示，只处理UI，不牵涉 回调
     *
     * @param visible
     */
    public void setVisibility4CircleViewAtTop(boolean visible) {
        if (!visible) {
            circleView.setVisibility(View.GONE);
            circleView.getBackground().setAlpha(0);
            progress.setAlpha(0);
            progress.stop();
            reset();
            return;
        }
        int endTarget = 0;
        if (!usingCustomStart) {
            endTarget = spinnerOffsetEnd + originalOffsetTop;
        } else {
            endTarget = spinnerOffsetEnd;
        }
        setTargetOffsetTopAndBottom(endTarget - currentTargetOffsetTop, true);
        startScaleUpAnimation(refreshAnimatorListener);
    }

    /**
     * CircleView 可见，在 顶部展示，只处理UI，不牵涉 回调
     *
     * @param visible
     */
    public void setVisibility4CircleViewAtBottom(boolean visible) {
        if (!visible) {
            circleView.setVisibility(View.GONE);
            circleView.getBackground().setAlpha(0);
            progress.setAlpha(0);
            progress.stop();
            reset();
            return;
        }
        progress.setAlpha(MAX_ALPHA);
        progress.start();
        circleView.setVisibility(View.VISIBLE);
        circleView.getBackground().setAlpha(MAX_ALPHA);
        circleView.bringToFront();
        circleView.setScaleX(1F);
        circleView.setScaleY(1F);
    }

    protected void startScaleDownAnimation(AnimationListener listener, final boolean refreshing) {
        scaleDownAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                setAnimationProgress(1 - interpolatedTime);
            }
        };
        scaleDownAnimation.setAnimationListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);
                RefreshLayout.this.refreshing = refreshing;
            }
        });

        scaleDownAnimation.setDuration(SCALE_DOWN_DURATION);
        circleView.setAnimationListener(listener);
        circleView.clearAnimation();
        circleView.startAnimation(scaleDownAnimation);
    }

    private void startProgressAlphaStartAnimation() {
        alphaStartAnimation = startAlphaAnimation(progress.getAlpha(), STARTING_PROGRESS_ALPHA);
    }

    private void startProgressAlphaMaxAnimation() {
        alphaMaxAnimation = startAlphaAnimation(progress.getAlpha(), MAX_ALPHA);
    }

    private Animation startAlphaAnimation(final int startingAlpha, final int endingAlpha) {
        // Pre API 11, alpha is used in place of scale. Don't also use it to
        // show the trigger point.
        if (scale && isAlphaUsedForScale()) {
            return null;
        }
        Animation alpha = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                progress.setAlpha((int) (startingAlpha + ((endingAlpha - startingAlpha) * interpolatedTime)));
            }
        };
        alpha.setDuration(ALPHA_ANIMATION_DURATION);
        // Clear out the previous animation listeners.
        circleView.setAnimationListener(null);
        circleView.clearAnimation();
        circleView.startAnimation(alpha);
        return alpha;
    }

    /**
     * Use {@link #setProgressBackgroundColorSchemeResource(int)}
     */
    public void setProgressBackgroundColor(int colorRes) {
        setProgressBackgroundColorSchemeResource(colorRes);
    }

    /**
     * Set the background color of the progress spinner disc.
     *
     * @param colorRes Resource id of the color.
     */
    public void setProgressBackgroundColorSchemeResource(@ColorRes int colorRes) {
        setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * Set the background color of the progress spinner disc.
     *
     * @param color
     */
    public void setProgressBackgroundColorSchemeColor(@ColorInt int color) {
        circleView.setBackgroundColor(color);
        progress.setBackgroundColor(color);
    }

    /**
     * Use {@link #setColorSchemeResources(int...)}
     */
    public void setColorScheme(int... colors) {
        setColorSchemeResources(colors);
    }

    /**
     * Set the color resources used in the progress animation from color resources.
     * The first color will also be the color of the bar that grows in response
     * to a user swipe gesture.
     *
     * @param colorResIds
     */
    public void setColorSchemeResources(int... colorResIds) {
        final Context context = getContext();
        int[] colorRes = new int[colorResIds.length];
        for (int i = 0; i < colorResIds.length; i++) {
            colorRes[i] = ContextCompat.getColor(context, colorResIds[i]);
        }
        setColorSchemeColors(colorRes);
    }

    /**
     * Set the colors used in the progress animation. The first
     * color will also be the color of the bar that grows in response to a user
     * swipe gesture.
     *
     * @param colors
     */
    public void setColorSchemeColors(@ColorInt int... colors) {
        ensureTarget();
        progress.setColorSchemeColors(colors);
    }

    /**
     * @return Whether the SwipeRefreshWidget is actively showing refresh
     * progress.
     */
    public boolean isRefreshing() {
        return refreshing;
    }

    /**
     * 找到执行 xml文件中  SwipeRefreshLayout 的 直接子控件
     */
    private void ensureTarget() {
        if (targetView != null) {
            return;
        }
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (!child.equals(circleView)) {
                targetView = child;
            }
            if (targetView instanceof RecyclerView) {
                final RecyclerView recyclerView = (RecyclerView) targetView;
                break;
            }
        }

    }


    /**
     * 触发 下拉刷新的 安全距离， 单位 dp
     *
     * @param distance
     */
    public void setRefreshDistanceToTriggerSync(int distance) {
        refreshMaxDragDistance = getResources().getDisplayMetrics().density * distance;
    }

    /**
     * 触发 下拉刷新的 安全距离， 单位 px
     *
     * @param distance
     */
    public int getRefreshDistanceToTriggerSync() {
        return (int) refreshMaxDragDistance;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }
        if (targetView == null) {
            ensureTarget();
        }
        if (targetView == null) {
            return;
        }
        final View child = targetView;
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        circleViewLayoutAtTop();
    }

    private void circleViewLayoutAtTop() {
        int circleWidth = circleView.getMeasuredWidth();
        int circleHeight = circleView.getMeasuredHeight();
        circleView.layout((width / 2 - circleWidth / 2), currentTargetOffsetTop, (width / 2 + circleWidth / 2), currentTargetOffsetTop + circleHeight);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (targetView == null) {
            ensureTarget();
        }
        if (targetView == null) {
            return;
        }
        targetView.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY));
        circleView.measure(MeasureSpec.makeMeasureSpec(circleDiameter, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(circleDiameter, MeasureSpec.EXACTLY));
        mCircleViewIndex = -1;
        // Get the index of the circleview.
        for (int index = 0; index < getChildCount(); index++) {
            if (getChildAt(index) == circleView) {
                mCircleViewIndex = index;
                break;
            }
        }
    }

    /**
     * Get the diameter of the progress circle that is displayed as part of the
     * swipe to refresh layout.
     *
     * @return Diameter in pixels of the progress circle view.
     */
    public int getProgressCircleDiameter() {
        return circleDiameter;
    }

    /**
     * SwipeRefreshLayout 的直接控件，知否可以继续展示上一条的数据
     * 如果是 RecyclerView.canScrollVertically(-1);  //true表示是否能向下滚动，false表示已经滚动到顶部
     *
     * @return true 表示 子控件 已经滑动到 顶部
     */
    public boolean canChildScrollUp() {
        if (childScrollUpCallback != null) {
            return childScrollUpCallback.canChildScrollUp(this, targetView);
        }
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (targetView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) targetView;
                return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(targetView, -1) || targetView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(targetView, -1);
        }
    }

    /**
     * SwipeRefreshLayout 的直接控件，是否继续展示下一条 的数据
     * 如果是 RecyclerView.canScrollVertically(1);  //true表示是否能向上滚动，false表示已经滚动到底部
     */
    public boolean canChildScrollDown(View targetView) {
        if (targetView instanceof RecyclerView) {
            /*true表示是否能向上滚动，false表示已经滚动到底部*/
            return ((RecyclerView) targetView).canScrollVertically(1);
        }
        return false;
    }

    /**
     * SwipeRefreshLayout 的直接控件，是否继续展示下一条 的数据
     * 如果是 RecyclerView.canScrollVertically(1);  //true表示是否能向上滚动，false表示已经滚动到底部
     */
    public boolean canChildScrollDown() {
        if (childScrollDownCallback != null) {
            return childScrollDownCallback.canChildScrollDown(this, targetView);
        }
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (targetView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) targetView;
                if (absListView.getChildCount() > 0) {
                    int lastChildBottom = absListView.getChildAt(absListView.getChildCount() - 1).getBottom();
                    return absListView.getLastVisiblePosition() == absListView.getAdapter().getCount() - 1 && lastChildBottom <= absListView.getMeasuredHeight();
                } else {
                    return false;
                }

            } else {
                return ViewCompat.canScrollVertically(targetView, 1) || targetView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(targetView, 1);
        }
    }

    public void setOnChildScrollUpCallback(@Nullable OnChildScrollUpCallback callback) {
        childScrollUpCallback = callback;
    }

    public void setOnChildScrollDownCallback(@Nullable OnChildScrollDownCallback callback) {
        childScrollDownCallback = callback;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();
        final int action = MotionEventCompat.getActionMasked(ev);
        int pointerIndex;

        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
            mReturningToStart = false;
        }

        if (!getRefreshEnable() || mReturningToStart || canChildScrollUp() || isRefreshing() || nestedScrollInProgress) {
            // Fail fast if we're not in a state where a swipe is possible
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!isLoadingMore()) {
                    setTargetOffsetTopAndBottom(originalOffsetTop - circleView.getTop(), true);
                }
                mActivePointerId = ev.getPointerId(0);
                isBeingDragged = false;

                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                mInitialDownY = ev.getY(pointerIndex);
                break;

            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {
                    Log.e(LOG_TAG, "Got ACTION_MOVE event but don't have an active pointer id.");
                    return false;
                }

                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                final float y = ev.getY(pointerIndex);
                startDragging(y);
                break;

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                break;
        }

        return isBeingDragged;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean b) {
        // if this is a List < L or another view that doesn't support nested
        // scrolling, ignore this request so that the vertical scroll event
        // isn't stolen
        if ((android.os.Build.VERSION.SDK_INT < 21 && targetView instanceof AbsListView) || (targetView != null && !ViewCompat.isNestedScrollingEnabled(targetView))) {
            // Nope.
        } else {
            super.requestDisallowInterceptTouchEvent(b);
        }
    }

    // NestedScrollingParent

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return getRefreshEnable() && !mReturningToStart && !isRefreshing() && (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        // Reset the counter of how much leftover scroll needs to be consumed.
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
        // Dispatch up to the nested parent
        startNestedScroll(axes & ViewCompat.SCROLL_AXIS_VERTICAL);
        swipeDownUnconsumed = 0;
        swipeUpUnconsumed = 0;
        nestedScrollInProgress = true;
    }

    /**
     * 对于 下拉刷新来说，这里处理手指向上滑动的 过程，就是取消 下拉 刷新的过程
     * 对于 上拉加载来说，这里处理手指向下滑动的 过程，就是取消 上拉 加载的过程
     *
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // If we are in the middle of consuming, a scroll, then we want to move the spinner back up
        // before allowing the list to scroll
        if (dy > 0 && swipeDownUnconsumed > 0) {
            if (dy > swipeDownUnconsumed) {
                consumed[1] = dy - (int) swipeDownUnconsumed;
                swipeDownUnconsumed = 0;
            } else {
                swipeDownUnconsumed -= dy;
                consumed[1] = dy;
            }
            if (!isLoadingMore()) {
                //LogUtil.e("onNestedPreScroll... ");
                moveHead(swipeDownUnconsumed);
            }
        } else if (dy < 0 && swipeUpUnconsumed > 0) {
            if (dy > swipeUpUnconsumed) {
                consumed[1] = -dy + (int) swipeUpUnconsumed;
                swipeUpUnconsumed = 0;
            } else {
                swipeUpUnconsumed += dy;
                consumed[1] = dy;
            }
            if (!isRefreshing()) {
                //TODO  取消 上拉 加载
                isLoadingMoreCancel = true;
                moveFoot(swipeUpUnconsumed);
            }
        }

        // If a client layout is using a custom start position for the circle
        // view, they mean to hide it again before scrolling the child view
        // If we get back to swipeDownUnconsumed == 0 and there is more to go, hide
        // the circle so it isn't exposed if its blocking content is moved
        if (usingCustomStart && dy > 0 && swipeDownUnconsumed == 0 && Math.abs(dy - consumed[1]) > 0) {
            circleView.setVisibility(View.GONE);
        }

        // Now let our nested parent consume the leftovers
        final int[] parentConsumed = mParentScrollConsumed;
        if (dispatchNestedPreScroll(dx - consumed[0], dy - consumed[1], parentConsumed, null)) {
            consumed[0] += parentConsumed[0];
            consumed[1] += parentConsumed[1];
        }
    }

    @Override
    public int getNestedScrollAxes() {
        //LogTrack.e("  ");
        return nestedScrollingParentHelper.getNestedScrollAxes();
    }

    /**
     * 停止 手势操作的时候，一定会调用这个方法，并判断需要 回调 刷新 | 加载更多
     *
     * @param target
     */
    @Override
    public void onStopNestedScroll(View target) {
        nestedScrollingParentHelper.onStopNestedScroll(target);
        nestedScrollInProgress = false;

        /**
         * 回调 下拉刷新接口， 开始操作
         */
        if (swipeDownUnconsumed > 0 && swipeUpUnconsumed <= 0) {
            //LogUtil.e("onStopNestedScroll ... ");
            //LogUtil.e("currentTargetOffsetTop = " + currentTargetOffsetTop);
            finishHead(swipeDownUnconsumed);
            swipeDownUnconsumed = 0;
        }
        /**
         * 回调 上拉加载更多接口，开始操作
         */
        if (swipeUpUnconsumed > 0 && swipeDownUnconsumed <= 0) {
            finishFoot(swipeUpUnconsumed);
            swipeUpUnconsumed = 0;
        }
        // Dispatch up our nested parent
        stopNestedScroll();
    }

    private void autoLoadMore() {
        boolean isAtBottom = !ViewCompat.canScrollVertically(targetView, 1);
        if (isSmallGap() && isAtBottom) {
            setLoadingMore(true);
        }
    }

    /**
     * 对于 下拉刷新来说，这里处理手指向下滑动的 过程，就是开始 下拉 刷新的过程
     * 对于 上拉加载来说，这里处理手指向上滑动的 过程，就是开始 上拉 加载的过程
     *
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(final View target, final int dxConsumed, final int dyConsumed, final int dxUnconsumed, final int dyUnconsumed) {
        // Dispatch up to the nested parent first
        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, parentOffsetInWindow);
        /**
         * 嵌套滚动从下往上起作用，因为我们有时在两个嵌套滚动视图之间，我们需要一种方法，以便能够知道什么时候
         * 任何嵌套滚动父项已停止处理事件。 我们通过使用“窗口中的偏移”功能来查看我们是否已从事件中移除。
         * 这是一个体面的指示是否我们应该接管事件流或不。
         */
        /**
         * 嵌套的滑动事件，只有从底往上 才会起作用；
         */
        final int dy = dyUnconsumed + parentOffsetInWindow[1];
        //LogTrack.e("onNestedScroll ... ");
        /**
         * 向下拉的事件  处理
         */
        if (dy < 0 && !canChildScrollUp() && !isLoadingMore()) {
            swipeDownUnconsumed += Math.abs(dy);
            moveHead(swipeDownUnconsumed);
        } else if (dy > 0 && !canChildScrollDown() && !isLoadingMore() && !isRefreshing()) {
            swipeUpUnconsumed += Math.abs(dy);
            isLoadingMoreCancel = false;
            //LogTrack.e("swipeUpUnconsumed = " + swipeUpUnconsumed);
            moveFoot(swipeUpUnconsumed);
        }
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        nestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return nestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return nestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        nestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return nestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return nestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return nestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return nestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return nestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    private boolean isAnimationRunning(Animation animation) {
        return animation != null && animation.hasStarted() && !animation.hasEnded();
    }

    /**
     * 关联 上下拉 滑动事件
     *
     * @param overScrollTop
     */
    private void moveFoot(float overScrollTop) {
        /**
         * overScrollTop，这个数值会越来越大，一直到匹配父窗体的高度
         */
        if (overScrollTop > 10 && getLoadMoreEnable()) {
            onLoadingMoreCritical();
        } else {
            onLoadingMoreNone();
        }
    }

    /**
     * 手指从 底 向上滑动， 底部loading视图会跟着滑动， 但是offset会有一个最大值
     *
     * @param offset
     */
    private void circleViewLayoutAtBottom(int offset) {
        int circleWidth = circleView.getMeasuredWidth();
        int circleHeight = circleView.getMeasuredHeight();
        circleView.layout((width / 2 - circleWidth / 2), height - currentTargetOffsetTop - offset - circleHeight, (width / 2 + circleWidth / 2), height - currentTargetOffsetTop - offset);
    }

    private void finishFoot(float overScrollTop) {
        /*
        * 上拉刷新时，触发状态的临界值，
        * */
        if (!getLoadMoreEnable()) {
            onLoadingMoreNone();
            return;
        }
        //LogTrack.e("-- " + overScrollTop);

        if (!isLoadingMoreCancel && (overScrollTop > (isSmallGap ? footOffsetMax * 0.9F : 10))) {
            setLoadingMore(true);
        } else {
            setLoadingMore(false);
            startScaleDownAnimation(refreshAnimatorListener, refreshing);
        }
    }

    private boolean isSmallGap;

    public void setSmallGap(boolean enable) {
        isSmallGap = enable;
    }

    private boolean isSmallGap() {
        return isSmallGap;
    }

    /**
     * 关联 上下拉 滑动事件，移动 顶部转圈圈的 CircleView
     *
     * @param overScrollTop
     */
    private void moveHead(float overScrollTop) {
        //LogUtil.e("moveHead ... overScrollTop = " + overScrollTop);
        progress.showArrow(true);
        float originalDragPercent = overScrollTop / refreshMaxDragDistance;

        float dragPercent = Math.min(1f, Math.abs(originalDragPercent));
        float adjustedPercent = (float) Math.max(dragPercent - .4, 0) * 5 / 3;
        float extraOS = Math.abs(overScrollTop) - refreshMaxDragDistance;
        float slingshotDist = usingCustomStart ? spinnerOffsetEnd - originalOffsetTop : spinnerOffsetEnd;
        float tensionSlingshotPercent = Math.max(0, Math.min(extraOS, slingshotDist * 2) / slingshotDist);
        float tensionPercent = (float) ((tensionSlingshotPercent / 4) - Math.pow((tensionSlingshotPercent / 4), 2)) * 2f;
        float extraMove = (slingshotDist) * tensionPercent * 2;

        int targetY = originalOffsetTop + (int) ((slingshotDist * dragPercent) + extraMove);
        // where 1.0f is a full circle
        if (circleView.getVisibility() != View.VISIBLE) {
            circleView.setVisibility(View.VISIBLE);
        }
        if (!scale) {
            ViewCompat.setScaleX(circleView, 1f);
            ViewCompat.setScaleY(circleView, 1f);
        }
        if (scale) {
            setAnimationProgress(Math.min(1f, overScrollTop / refreshMaxDragDistance));
        }
        if (overScrollTop < refreshMaxDragDistance) {
            if (progress.getAlpha() > STARTING_PROGRESS_ALPHA && !isAnimationRunning(alphaStartAnimation)) {
                startProgressAlphaStartAnimation();
            }
        } else {
            if (progress.getAlpha() < MAX_ALPHA && !isAnimationRunning(alphaMaxAnimation)) {
                startProgressAlphaMaxAnimation();
            }
        }
        float strokeStart = adjustedPercent * .8f;
        progress.setStartEndTrim(0f, Math.min(MAX_PROGRESS_ANGLE, strokeStart));
        progress.setArrowScale(Math.min(1f, adjustedPercent));

        float rotation = (-0.25f + .4f * adjustedPercent + tensionPercent * 2) * .5f;
        progress.setProgressRotation(rotation);
        //LogUtil.e("targetY = " + targetY);
        headOffsetMax = (headOffsetMax < targetY) ? targetY : headOffsetMax;
        setTargetOffsetTopAndBottom(targetY - currentTargetOffsetTop, true);
    }

    /**
     * 执行 下拉刷新，手指一直滑动，直到CircleView不能继续向下滑动，松开手指，一定会调用这个方法
     *
     * @param overScrollTop
     */
    private void finishHead(float overScrollTop) {
        if (overScrollTop > refreshMaxDragDistance) {
            setRefreshing(true, true);
        } else {
            /**
             * 取消 下拉刷新
             */
            refreshing = false;
            progress.setStartEndTrim(0f, 0f);
            AnimationListener listener = null;
            if (!scale) {
                listener = new SimpleAnimationListener() {

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (!scale) {
                            startScaleDownAnimation(null, refreshing);
                        }
                    }
                };
            }
            animateOffsetToStartPosition(currentTargetOffsetTop, listener);
            progress.showArrow(false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        int pointerIndex = -1;

        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
            mReturningToStart = false;
        }

        if (!getRefreshEnable() || mReturningToStart || canChildScrollUp() || isRefreshing() || nestedScrollInProgress) {
            // Fail fast if we're not in a state where a swipe is possible
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                isBeingDragged = false;
                break;

            case MotionEvent.ACTION_MOVE: {
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    Log.e(LOG_TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }

                final float y = ev.getY(pointerIndex);
                startDragging(y);

                if (isBeingDragged) {
                    final float overScrollTop = (y - mInitialMotionY) * DRAG_RATE;
                    if (overScrollTop > 0 && !isLoadingMore()) {
                        //LogUtil.e("onTouchEvent ... ");
                        moveHead(overScrollTop);
                    } else {
                        return false;
                    }
                }
                break;
            }
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                pointerIndex = MotionEventCompat.getActionIndex(ev);
                if (pointerIndex < 0) {
                    Log.e(LOG_TAG, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
                mActivePointerId = ev.getPointerId(pointerIndex);
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;

            case MotionEvent.ACTION_UP: {
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    Log.e(LOG_TAG, "Got ACTION_UP event but don't have an active pointer id.");
                    return false;
                }
                if (isBeingDragged) {
                    final float y = ev.getY(pointerIndex);
                    final float overScrollTop = (y - mInitialMotionY) * DRAG_RATE;
                    isBeingDragged = false;
                    //LogUtil.e("currentTargetOffsetTop = " + currentTargetOffsetTop);
                    finishHead(overScrollTop);
                }
                mActivePointerId = INVALID_POINTER;
                return false;
            }
            case MotionEvent.ACTION_CANCEL:
                return false;
        }

        return true;
    }

    private void startDragging(float y) {
        final float yDiff = y - mInitialDownY;
        if (yDiff > touchSlop && !isBeingDragged) {
            mInitialMotionY = mInitialDownY + touchSlop;
            isBeingDragged = true;
            progress.setAlpha(STARTING_PROGRESS_ALPHA);
        }
    }

    private void headOffsetToCorrectPositionAnimate(int from, AnimationListener listener) {
        headFrom = from;
        headToCorrectPositionAnimate.reset();

        headToCorrectPositionAnimate.setDuration(ANIMATE_TO_TRIGGER_DURATION);
        headToCorrectPositionAnimate.setInterpolator(decelerateInterpolator);
        if (listener != null) {
            circleView.setAnimationListener(listener);
        }
        circleView.clearAnimation();
        circleView.startAnimation(headToCorrectPositionAnimate);
    }

    private void animateOffsetToStartPosition(int from, AnimationListener listener) {
        if (scale) {
            startScaleDownReturnToStartAnimation(from, listener);
        } else {
            headFrom = from;
            animateToStartPosition.reset();
            animateToStartPosition.setDuration(ANIMATE_TO_START_DURATION);
            animateToStartPosition.setInterpolator(decelerateInterpolator);
            if (listener != null) {
                circleView.setAnimationListener(listener);
            }
            circleView.clearAnimation();
            circleView.startAnimation(animateToStartPosition);
        }
    }

    /**
     * 回到矫正的位置， CircleView一直在顶部转圈
     */
    private final Animation headToCorrectPositionAnimate = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            int targetTop = 0;
            int endTarget = 0;
            if (!usingCustomStart) {
                endTarget = spinnerOffsetEnd - Math.abs(originalOffsetTop);
            } else {
                endTarget = spinnerOffsetEnd;
            }
            targetTop = (headFrom + (int) ((endTarget - headFrom) * interpolatedTime));
            int offset = targetTop - circleView.getTop();
            setTargetOffsetTopAndBottom(offset, false);
            progress.setArrowScale(1 - interpolatedTime);
        }
    };

    protected void moveToStart(float interpolatedTime) {
        int targetTop = 0;
        targetTop = (headFrom + (int) ((originalOffsetTop - headFrom) * interpolatedTime));
        int offset = targetTop - circleView.getTop();
        //LogUtil.e("进来了 ... moveToStart");
        setTargetOffsetTopAndBottom(offset, false);
    }

    private final Animation animateToStartPosition = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            moveToStart(interpolatedTime);
        }
    };

    private void startScaleDownReturnToStartAnimation(int from, AnimationListener listener) {
        headFrom = from;
        if (isAlphaUsedForScale()) {
            startingScale = progress.getAlpha();
        } else {
            startingScale = ViewCompat.getScaleX(circleView);
        }
        scaleDownToStartAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                float targetScale = (startingScale + (-startingScale * interpolatedTime));
                setAnimationProgress(targetScale);
                moveToStart(interpolatedTime);
            }
        };

        scaleDownToStartAnimation.setDuration(SCALE_DOWN_DURATION);
        if (listener != null) {
            circleView.setAnimationListener(listener);
        }
        circleView.clearAnimation();
        circleView.startAnimation(scaleDownToStartAnimation);
    }

    /**
     * 将 circleView 展示出来
     *
     * @param offset
     * @param requiresUpdate
     */
    protected void setTargetOffsetTopAndBottom(int offset, boolean requiresUpdate) {
        circleView.bringToFront();
        ViewCompat.offsetTopAndBottom(circleView, offset);
        currentTargetOffsetTop = circleView.getTop();
        if (requiresUpdate && android.os.Build.VERSION.SDK_INT < 11) {
            invalidate();
        }
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == mActivePointerId) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = ev.getPointerId(newPointerIndex);
        }
    }


    public interface OnChildScrollUpCallback {
        boolean canChildScrollUp(RefreshLayout parent, @Nullable View child);
    }

    public interface OnChildScrollDownCallback {
        boolean canChildScrollDown(RefreshLayout parent, @Nullable View child);
    }

    /**
     * 可以进行 下拉刷新
     *
     * @param enabled
     */
    public void setRefreshEnable(boolean enabled) {
        this.refreshEnable = enabled;
        if (!enabled) {
            reset();
        }
    }

    /**
     * 必须 在 调用 stopRefreshLayout  之前调用次 方法
     *
     * @param enabled
     */
    public void setLoadMoreEnable(boolean enabled) {
        this.loadMoreEnable = enabled;
    }

    public boolean getLoadMoreEnable() {
        return loadMoreEnable;
    }

    public boolean getRefreshEnable() {
        return refreshEnable;
    }

    /**
     * 正在加载更多
     */
    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    /**
     * 设置 加载更多
     */
    public void setLoadingMore(boolean isLoadingMore) {
        this.isLoadingMore = isLoadingMore;
        if (isLoadingMore) {
            onLoadingMoreStart();
        } else {
            onLoadingMoreFinish();
        }
    }


    /**
     * 停止下拉刷新 上拉加载
     */
    public void stopRefreshLayout() {
        setRefreshing(false);
        setLoadingMore(false);
        setFootTextLabel(getLoadMoreEnable() ? "上拉加载" : loadMoreFailLabel);
        setFootLoadingVisibility(false);
    }

    /**
     * UI  和 回调方法，都响应 下拉刷新功能
     */
    public void autoRefresh() {
        if (!isRefreshing() && !isLoadingMore()) {
            setRefreshing(true);
            onRefreshStart();
            setVisibility4CircleViewAtTop(true);
        }
    }

    /**
     * 设置 上拉加载的 最小滑动距离
     *
     * @param distance 单位 dp 默认12dp
     */
    public RefreshLayout setLoadMoreMinDistance(int distance) {
        this.loadMoreMinDistance = (int) dp2px(distance);
        return this;
    }

    /**
     * 触发 上拉加载 的 最小距离
     *
     * @return
     */
    public int getLoadMoreMinDistance() {
        return loadMoreMinDistance;
    }

    public RefreshLayout setOnFootViewChangerListener(OnFootViewChangerListener onFootViewChangerListener) {
        this.onFootViewChangerListener = onFootViewChangerListener;
        return this;
    }

    private TextView footTextView;
    private View footLoadingView;

    public void setFootLoadingView(View view) {
        footLoadingView = view;
        footLoadingView.setVisibility(View.GONE);
    }

    private void setFootLoadingVisibility(boolean enable) {
        if (footLoadingView == null) {
            return;
        }
        footLoadingView.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    public void setFootTextView(TextView view) {
        footTextView = view;
    }

    private void setFootTextLabel(String text) {
        if (footTextView != null) {
            footTextView.setText(text);
        }
    }

    private String loadMoreFailLabel = "没有更多数据";

    public void setLoadMoreFailLabel(String text) {
        loadMoreFailLabel = text;
        setFootTextLabel(text);
    }

    /**
     * 加载更多，没有响应上拉加载
     */
    private void onLoadingMoreNone() {
        setFootTextLabel(getLoadMoreEnable() ? "上拉加载" : loadMoreFailLabel);
        setFootLoadingVisibility(false);
        if (onFootViewChangerListener != null) {
            onFootViewChangerListener.onLoadMoreNone();
        }
    }

    /**
     * 加载更多，到了临界状态
     */
    private void onLoadingMoreCritical() {
        setFootTextLabel("松开后加载");
        if (onFootViewChangerListener != null) {
            onFootViewChangerListener.onLoadMoreCritical();
        }
    }

    /**
     * 关联 下拉刷新 的回调
     */
    private void onRefreshStart() {
        if (onRefreshListener != null && isRefreshing()) {
            onRefreshListener.onRefresh();
        }

    }

    /**
     * 加载更多，开始
     */
    private void onLoadingMoreStart() {
        setFootTextLabel("加载中");
        setFootLoadingVisibility(true);
        if (onRefreshListener != null) {
            onRefreshListener.onLoadMore();
        }
        if (onFootViewChangerListener != null) {
            onFootViewChangerListener.onLoadMoreStart();
        }
    }

    /**
     * 加载更多，结束
     */
    private void onLoadingMoreFinish() {
        setFootTextLabel(getLoadMoreEnable() ? "上拉加载" : loadMoreFailLabel);
        if (onFootViewChangerListener != null) {
            onFootViewChangerListener.onLoadMoreFinish();
        }
    }

    /**
     * 数据转换: dp---->px
     */
    public float dp2px(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

    private class SimpleAnimationListener implements AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
