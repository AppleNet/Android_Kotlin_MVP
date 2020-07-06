package com.example.llcgs.android_kotlin.customview.selfview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.llcgs.android_kotlin.customview.utils.Utils;


/**
 *
 *
 * */
public class Dashboard extends View {

    private static final int ANGLE = 120;
    private static final float RADIUS = Utils.dp2px(150);
    private static final int LENGTH = (int) Utils.dp2px(100);

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path dash = new Path();
    private PathDashPathEffect effect;

    public Dashboard(Context context) {
        super(context);
    }

    /**
     *  给系统自动加载的时候 使用的
     *
     * */
    public Dashboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        // 不填充
        paint.setStyle(Paint.Style.STROKE);
        // 设置画笔的粗细
        paint.setStrokeWidth(Utils.dp2px(2));

        Path arc = new Path();
        arc.addArc((float) getWidth() / 2 - RADIUS,
                (float) getHeight() / 2 -RADIUS,
                (float) getWidth() / 2 + RADIUS,
                (float) getHeight() / 2 + RADIUS,
                90 + ANGLE / 2,
                360 - ANGLE);
        PathMeasure pathMeasure = new PathMeasure(arc, false);

        // 画虚线
        // 每条虚线 都是一个长方形
        dash.addRect(0, 0, Utils.dp2px(2), Utils.dp2px(10), Path.Direction.CW);
        effect = new PathDashPathEffect(dash,
                (pathMeasure.getLength() - Utils.dp2px(2)) / 20,
                0,
                PathDashPathEffect.Style.ROTATE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画弧
        canvas.drawArc(
                (float) getWidth() / 2 - RADIUS,
                (float) getHeight() / 2 -RADIUS,
                (float) getWidth() / 2 + RADIUS,
                (float) getHeight() / 2 + RADIUS,
                90 + ANGLE / 2,
                360 - ANGLE,
                false,
                paint);

        // 画虚线
        paint.setPathEffect(effect);
        canvas.drawArc(
                (float) getWidth() / 2 - RADIUS,
                (float) getHeight() / 2 -RADIUS,
                (float) getWidth() / 2 + RADIUS,
                (float) getHeight() / 2 + RADIUS,
                90 + ANGLE / 2,
                360 - ANGLE,
                false,
                paint);
        paint.setPathEffect(null);

        // 画指针
        canvas.drawLine(
                getWidth() / 2,
                getHeight() / 2,
                (float) Math.cos(Math.toRadians(getAngleFromMark(5))) * LENGTH + (float) getWidth() / 2,
                (float) Math.sin(Math.toRadians(getAngleFromMark(5))) * LENGTH + (float) getHeight() / 2,
                paint);
    }

    int getAngleFromMark(int mark) {
        return (int) (90 + (float) ANGLE / 2 + (360 - (float) ANGLE) / 20 * mark);
    }
}
