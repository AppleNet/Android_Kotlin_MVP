package com.example.llcgs.android_kotlin.customview.selfview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.io.IOException;
import java.io.InputStream;

public class BigView extends View implements GestureDetector.OnGestureListener, View.OnTouchListener {

    /**
     *  创建绘制矩形空间
     * */
    private Rect mRect;
    /**
     *  Bitmap 辅助类
     * */
    private BitmapFactory.Options options;
    /**
     *  手势
     * */
    private GestureDetector gestureDetector;
    /**
     *  滑动
     * */
    private Scroller scroller;
    /**
     *  上下文
     * */
    private Context context;
    /**
     *  bitmap
     * */
    private Bitmap bitmap;
    /**
     *  图片的宽度
     * */
    private int mImageWidth;
    /**
     *  图片的高度
     * */
    private int mImageHeight;
    /**
     *
     * */
    private BitmapRegionDecoder bitmapRegionDecoder;
    /**
     *  view 的宽度
     * */
    private int mViewWidth;
    /**
     *   view 的高度
     * */
    private int mViewHeight;
    private int mScale;
    /**
     *  矩阵
     * */
    private Matrix matrix;

    public BigView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BigView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public BigView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public BigView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    public void init() {
        // 设置基础成员变量
        mRect = new Rect();
        options = new BitmapFactory.Options();
        gestureDetector = new GestureDetector(context, this);
        scroller = new Scroller(context);
        setOnTouchListener(this);
    }

    public void setImage(InputStream is) {
        // 不把整张图片加载进内存 依然可以获取宽高
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeStream(is, null, options);
        mImageWidth = options.outWidth;
        mImageHeight = options.outHeight;
        // 开启复用
        options.inMutable = true;
        // 设置图片格式
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //
        options.inJustDecodeBounds = false;
        // 创建区域解码器
        try {
            bitmapRegionDecoder = BitmapRegionDecoder.newInstance(is, false);
        } catch (Exception e) {
           //
        }
        matrix = new Matrix();
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取在 xml 中设置的这个自定义 View 的宽度和高度
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();

        mRect.left = 0;
        mRect.top = 0;
        mRect.right = mImageWidth;
        mScale = mViewWidth / mImageWidth;
        mRect.bottom = mViewHeight / mScale;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmapRegionDecoder == null) {
            return;
        }
        options.inBitmap = bitmap;
        bitmap = bitmapRegionDecoder.decodeRegion(mRect, options);
        matrix.setScale(mScale, mScale);
        canvas.drawBitmap(bitmap, matrix, null);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (!scroller.isFinished()) {
            scroller.forceFinished(true);
        }
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        scroller.fling(0, mRect.top, 0,
                (int) -velocityY, 0, 0, 0,
                mImageHeight - (mViewHeight / mScale));
        return false;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.isFinished()) {
            return;
        }

        if (scroller.computeScrollOffset()) {
            mRect.top = scroller.getCurrY();
            mRect.bottom = mRect.top + mViewHeight / mScale;
            invalidate();
        }
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mRect.offset(0, (int) distanceY);
        if (mRect.bottom > mImageHeight) {
            mRect.bottom = mImageHeight;
            mRect.top = mImageHeight - (mViewHeight / mScale);
        }

        if (mRect.top < 0) {
            mRect.top = 0;
            mRect.bottom = mViewHeight / mScale;
        }
        invalidate();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

}
