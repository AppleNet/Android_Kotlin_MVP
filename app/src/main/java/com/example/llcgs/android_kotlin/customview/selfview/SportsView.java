package com.example.llcgs.android_kotlin.customview.selfview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.llcgs.android_kotlin.customview.utils.Utils;

public class SportsView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final int RADIUS = 200;

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制环
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#000000"));
        paint.setStrokeWidth(Utils.dp2px(5));
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, paint);

        // 绘制进度条
        paint.setColor(Color.parseColor("#AAAAAA"));
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(
                getWidth() / 2 - RADIUS,
                getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS,
                getHeight() / 2 + RADIUS,
                -90,
                225,
                false,
                paint);

        // 绘制文字

    }
}
