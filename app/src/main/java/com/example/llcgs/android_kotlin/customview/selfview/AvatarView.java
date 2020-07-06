package com.example.llcgs.android_kotlin.customview.selfview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.llcgs.android_kotlin.R;
import com.example.llcgs.android_kotlin.customview.utils.Utils;


public class AvatarView extends View {

    private static final int WIDTH = (int) Utils.dp2px(300);
    private static final int PADDING = (int) Utils.dp2px(50);
    private static final int EDAGE = (int) Utils.dp2px(5);

    Bitmap bitmap;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    RectF saveAread = new RectF();
    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    /**
     *  给系统自动加载的时候 使用的
     *
     * */
    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = getAvatar(WIDTH);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        saveAread.set(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画边
        canvas.drawOval(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH, paint);
        // 离屏缓冲
        int saved = canvas.saveLayer(saveAread, paint);
        canvas.drawOval(
                PADDING + EDAGE,
                PADDING + EDAGE,
                PADDING + WIDTH - EDAGE,
                PADDING + WIDTH - EDAGE,
                paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, PADDING, PADDING, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saved);
    }


    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.zeus, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.mipmap.zeus, options);
    }
}
