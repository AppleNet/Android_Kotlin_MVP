package com.example.llcgs.android_kotlin.customview.selfview;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import com.example.llcgs.android_kotlin.R;

import java.io.IOException;

/**
 * com.example.llcgs.android_kotlin.customview.selfview.PhotoView
 *
 * @author liulongchao
 * @since 2020/11/19
 */
public class PhotoView extends View {

    private Bitmap bitmap;
    private Paint paint;
    private final Context mContext;

    private float originalOffsetX;
    private float originalOffsetY;
    private float offsetX;
    private float offsetY;

    private float smallScale;
    private float bigScale;
    private float currentScale;

    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;

    private final float OVER_SCALE_FACTOR = 1.5f;

    private boolean isEnlarge;

    private ObjectAnimator scaleAnimator;
    private OverScroller overScroller;

    public PhotoView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    private void init() {
        try {
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.kobe);
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            // 单指手势处理
            gestureDetector = new GestureDetector(mContext, new PhotoGestureDetector());
            // 关闭长按响应
            // gestureDetector.setIsLongpressEnabled(false);
            overScroller = new OverScroller(mContext);
            // 双指缩放手势处理
            scaleGestureDetector = new ScaleGestureDetector(mContext, new PhotoScaleGestureListener());
        } catch (Exception e) {
            //
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float scaleFaction = (currentScale - smallScale) / (bigScale - smallScale);
        // 当前放大比例为small时，scaleFaction = 0，不偏移
        canvas.translate(offsetX * scaleFaction, offsetY * scaleFaction);
        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originalOffsetX = (getWidth() - bitmap.getWidth()) / 2f;
        originalOffsetY = (getHeight() - bitmap.getHeight()) / 2f;

        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            // smallScale 是宽全屏
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
        } else {
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * OVER_SCALE_FACTOR;
        }
        currentScale = smallScale;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 动作相应事件 以 双指缩放优先
        boolean result = scaleGestureDetector.onTouchEvent(event);
        if (!scaleGestureDetector.isInProgress()) {
            result = gestureDetector.onTouchEvent(event);
        }

        return result;
    }

    private void fixOffset() {
        offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
        offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
    }

    private ObjectAnimator getScaleAnimation() {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0);
        }
        scaleAnimator.setFloatValues(smallScale, bigScale);
        return scaleAnimator;
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    /**
     * 单指手势处理类
     * <p>
     * 处理单击，双击，快速滑动等case
     */
    class PhotoGestureDetector extends GestureDetector.SimpleOnGestureListener {

        public PhotoGestureDetector() {
            super();
        }

        /**
         * up触发，单击或者不是双击的第二次点击或者不是长按 会触发
         *
         * @param e e
         * @return boolean
         */
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }

        /**
         * 长按
         *
         * @param e e
         */
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        /**
         * 滚动
         *
         * @param e1        e1 手指按下
         * @param e2        e2 当前的事件
         * @param distanceX distanceX 滑动的距离 旧位置-新位置
         * @param distanceY distanceY
         * @return boolean
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (isEnlarge) {
                offsetX -= distanceX;
                offsetY -= distanceY;
                fixOffset();
                invalidate();
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        /**
         * 惯性滑动  大于50dp/s
         *
         * @param e1        e1
         * @param e2        e2
         * @param velocityX velocityX 水平x轴方向的运动速度 px/s
         * @param velocityY velocityY y轴方向的运动速度
         * @return boolean
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (isEnlarge) {
                overScroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                        -(int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                        (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                        -(int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
                        (int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
                        300, 300);
                postOnAnimation(new FlingRunner());
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        /**
         * 处理点击效果「例如水波纹」，延迟100毫秒触发
         *
         * @param e e
         */
        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        /**
         * 按下事件，只需要关注onDown的返回值
         *
         * @param e e
         * @return boolean
         */
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        /**
         * 双击的第二次点击down时触发  双击触发时间 --> 40ms-300ms之间有效
         *
         * @param e e
         * @return boolean
         */
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            isEnlarge = !isEnlarge;
            if (isEnlarge) {
                offsetX = (e.getX() - getWidth() / 2f) -
                        (e.getX() - getWidth() / 2f) * bigScale / smallScale;
                offsetY = (e.getY() - getHeight() / 2f) -
                        (e.getY() - getHeight() / 2f) * bigScale / smallScale;
                fixOffset();
                getScaleAnimation().start();
            } else {
                getScaleAnimation().reverse();
            }
            return super.onDoubleTap(e);
        }

        /**
         * 双击的第二次down事件，move事件，up事件 都触发
         *
         * @param e e down事件就返回down move事件就返回move 。。
         * @return boolean
         */
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }

        /**
         * down up 时都可能触发，双击不触发
         * 延时300ms触发TAP事件
         * 300ms以内抬手，才会触发TAP中的 onSingleTapConfirmed
         * 300ms以后抬手，不是双击不是长按，则触发
         *
         * @param e e
         * @return boolean
         */
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            return super.onContextClick(e);
        }
    }

    /**
     * 双指缩放手势处理类
     */
    class PhotoScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

        float initScale;

        /**
         * 缩放中处理
         *
         * @param detector detector
         * @return boolean
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if((currentScale > smallScale && !isEnlarge) || (currentScale == smallScale && !isEnlarge)) {
                isEnlarge = !isEnlarge;
            }
            // 缩放因子
            currentScale = initScale * detector.getScaleFactor();
            invalidate();
            return false;
        }

        /**
         * 缩放前处理
         *
         * @param detector detector
         * @return boolean
         */
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            initScale = currentScale;
            return true;
        }

        /**
         * 缩放后处理
         *
         * @param detector detector
         */
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }

    class FlingRunner implements Runnable {
        @Override
        public void run() {
            if (overScroller.computeScrollOffset()) {
                offsetX = overScroller.getCurrX();
                offsetY = overScroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }

}
