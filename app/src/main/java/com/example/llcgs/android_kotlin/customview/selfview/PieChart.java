package com.example.llcgs.android_kotlin.customview.selfview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.llcgs.android_kotlin.customview.utils.Utils;


public class PieChart extends View {

    private static final int RADIUS = (int) Utils.dp2px(150);
    private static final int LENGTH = (int) Utils.dp2px(20);
    private static final int PULLED_OUT_INDEX = 2;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF bounds = new RectF();
    private int[] angles = {60, 100, 120, 80};
    private int[] colors = {
            Color.parseColor("#123456"),
            Color.parseColor("#654321"),
            Color.parseColor("#456789"),
            Color.parseColor("#987654")
    };

    /**
     *  给系统自动加载的时候 使用的
     *
     * */
    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(getWidth() / 2 - RADIUS,
                getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS,
                getHeight() / 2 + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int currentAngle = 0;
        for(int i = 0; i < angles.length; i++) {
            paint.setColor(colors[i]);
            canvas.save();
            if (i == PULLED_OUT_INDEX) {
                // 抽取一个
                canvas.translate(
                        (float) Math.cos(Math.toRadians(currentAngle + angles[i] / 2)) * LENGTH,
                        (float) Math.sin(Math.toRadians(currentAngle + angles[i] / 2)) * LENGTH
                );
            }
            canvas.drawArc(bounds, currentAngle, angles[i], true, paint);
            canvas.restore();
            currentAngle += angles[i];
        }
    }
}
