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
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * com.live.wallpaper.one.view.SWBigImgView
 *
 * @author liulongchao
 * @since 2020-07-24
 * <p>
 * 加载大图，长图
 */
public class BigView extends View implements GestureDetector.OnGestureListener, View.OnTouchListener {


    private static final String TAG = "SWBigImgView";

    /**
     * 创建绘制矩形空间
     */
    private Rect mRect;
    /**
     * Bitmap 辅助类
     */
    private BitmapFactory.Options options;
    /**
     * 手势
     */
    private GestureDetector gestureDetector;
    /**
     * 滑动
     */
    private Scroller scroller;
    /**
     * 上下文
     */
    private Context context;
    /**
     * bitmap
     */
    private Bitmap bitmap;
    /**
     * 图片的宽度
     */
    private float mImageWidth;
    /**
     * 图片的高度
     */
    private float mImageHeight;
    /**
     *
     */
    private BitmapRegionDecoder bitmapRegionDecoder;
    /**
     * view 的宽度
     */
    private float mViewWidth;
    /**
     * view 的高度
     */
    private float mViewHeight;
    private float mScale;
    /**
     * 矩阵
     */
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

    public void init() {
        // 设置基础成员变量
        mRect = new Rect();
        options = new BitmapFactory.Options();
        gestureDetector = new GestureDetector(context, this);
        scroller = new Scroller(context);
        setOnTouchListener(this);
    }

    /**
     * 通过 InputStream 加载一张大图
     */
    public void setImageFromInputStream(InputStream is) {
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


    /**
     * 将 InputStream 转换成 ByteArray 进行图片的展示
     *
     * @param inputStream inputStream
     */
    public void setImage(InputStream inputStream) {
        // 不把整张图片加载进内存 依然可以获取宽高
        // 设置为 true 之后 就只能获取到图片的信息，并不能获取到图片
        options.inJustDecodeBounds = true;
        inputStream2ByteArr(inputStream);
    }

    /**
     * RxJava 异步的将 InputStream 转换成 ByteArray
     * 防止InputStream太大产生耗时操作而 ANR
     *
     * @param inputStream inputStream
     */
    private void inputStream2ByteArr(final InputStream inputStream) {
        Single.just(inputStream)
                .flatMap((Function<InputStream, SingleSource<byte[]>>) inputStream1 -> {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buff = new byte[1024];
                    int len = 0;
                    while ((len = inputStream1.read(buff)) != -1) {
                        outputStream.write(buff, 0, len);
                        len = +len;
                        Log.d(TAG, "len: " + len);
                    }
                    inputStream1.close();
                    outputStream.close();
                    return Single.just(outputStream.toByteArray());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<byte[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(byte[] bytes) {
                        // 这里当 options.inJustDecodeBounds 设置为 true 的，如果将 options 传入decodeByteArray中，是获取不到 bitmap 的，decodeStream 也是一样的问题
                        // 所以这里不传，跟设置options.inJustDecodeBounds 设置为 true和 false 就没有关系了。
                        // 因为从网络上获取到的图片的 InputStream 或者 ByteArrayOutputStream等都是虚拟的，也就是说本地图片是不存在，如果想使用 options.inJustDecodeBounds = true; 那么需要将图片下载到本地
                        // 这个属性 options.inJustDecodeBounds = true这个参数是从一个已有的图片中获取信息
                        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        if (bitmap != null) {
                            Log.d(TAG, "bitmap.getWidth(): " + bitmap.getWidth() + ", bitmap.getHeight(): " + bitmap.getHeight());
                            if (options.outWidth == 0 || options.outHeight == 0) {
                                mImageWidth = bitmap.getWidth();
                                mImageHeight = bitmap.getHeight();
                            } else {
                                mImageWidth = options.outWidth;
                                mImageHeight = options.outHeight;
                            }
                            // 开启复用
                            options.inMutable = true;
                            // 设置图片格式
                            options.inPreferredConfig = Bitmap.Config.RGB_565;
                            //
                            options.inJustDecodeBounds = false;
                            // 创建区域解码器
                            try {
                                bitmapRegionDecoder = BitmapRegionDecoder.newInstance(bytes, 0, bytes.length, false);
                            } catch (Exception e) {
                                //
                                Log.d(TAG, "BitmapRegionDecoder 获取失败");
                            }
                            matrix = new Matrix();
                            requestLayout();
                            invalidate();
                            postInvalidate();
                        } else {
                            Log.d(TAG, "bitmap == null");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取在 xml 中设置的这个自定义 View 的宽度和高度
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();
        if (mViewWidth == 0 || mViewHeight == 0) {
            return;
        }

        if (mImageWidth == 0 || mImageHeight == 0) {
            return;
        }

        Log.d(TAG, "mViewWidth: " + mViewWidth + ", mViewHeight: " + mViewHeight);
        Log.d(TAG, "mImageWidth: " + mImageWidth + ", mImageHeight: " + mImageHeight);

        mRect.left = 0;
        mRect.top = 0;
        mRect.right = (int) mImageWidth;
        mScale = mViewWidth / mImageWidth;
        if (mScale == 0) {
            return;
        }
        Log.d(TAG, "mScale: " + mScale);
        mRect.bottom = (int) (mViewHeight / mScale);
        invalidate();
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmapRegionDecoder == null) {
            Log.d(TAG, "bitmapRegionDecoder == null");
            return;
        }
        options.inBitmap = bitmap;
        bitmap = bitmapRegionDecoder.decodeRegion(mRect, options);
        matrix.setScale(mScale, mScale);
        canvas.drawBitmap(bitmap, mRect, mRect, null);
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
                (int) (mImageHeight - (mViewHeight / mScale)));
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
            mRect.bottom = (int) (mRect.top + mViewHeight / mScale);
            invalidate();
        }
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mRect.offset(0, (int) distanceY);
        if (mRect.bottom > mImageHeight) {
            mRect.bottom = (int) mImageHeight;
            mRect.top = (int) (mImageHeight - (mViewHeight / mScale));
        }

        if (mRect.top < 0) {
            mRect.top = 0;
            mRect.bottom = (int) (mViewHeight / mScale);
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
