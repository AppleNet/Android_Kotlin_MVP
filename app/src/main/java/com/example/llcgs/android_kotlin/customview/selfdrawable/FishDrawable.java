package com.example.llcgs.android_kotlin.customview.selfdrawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * com.example.llcgs.android_kotlin.customview.selfdrawable.FishDrawable
 *
 * @author liulongchao
 * @since 2020/10/15
 */
public class FishDrawable extends Drawable {




    private Path mPath;
    private Paint mPaint;

    // 鱼的重心
    private PointF middlePoint;
    // 鱼的主要朝向角度，鱼头与X轴的夹角
    private float fishMainAngle = 0.0f;

    /**
     * 鱼的长度值
     * */
    // 绘制鱼头的半径
    private final int HEAD_RADIUS = 100;
    // 鱼身的长度
    private float BODY_LENGTH = HEAD_RADIUS * 3.2f;
    // 鱼鳍的长度
    private float FINS_LENGTH = 1.3f * HEAD_RADIUS;
    // 寻找鱼鳍起始点坐标点线长
    private float FIND_FINS_LENGTH = 0.9f * HEAD_RADIUS;


    private final int OTHER_ALPHA = 110;

    FishDrawable() {
        init();
    }

    private void init() {
        mPath = new Path();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setARGB(OTHER_ALPHA, 244, 92, 71);

        middlePoint = new PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        // 绘制小鱼
        float fishAngle = fishMainAngle;
        // 鱼头圆心坐标
        PointF headPoint = calculatePoint(middlePoint, BODY_LENGTH / 2, fishAngle);
        // 绘制鱼头
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint);

        // 鱼鳍 (二阶贝塞尔曲线)
        PointF rightFinsPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle - 110);
        makeFins(canvas, rightFinsPoint, fishAngle);

    }

    private void makeFins(Canvas canvas, PointF startPoint, float fishAngle) {
        float controlAngle = 115;
        //
        PointF endPoint = calculatePoint(startPoint, FINS_LENGTH, fishAngle - 180);
        PointF controlPoint = calculatePoint(startPoint, FINS_LENGTH * 1.8f, fishAngle - controlAngle);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    /**
     * 计算旋转角度之后的坐标
     *
     * @param startPoint 起始点坐标
     * @param length     要求的点到起始点的直线距离 -- 线长
     * @param angle      鱼当前朝向的角度
     */
    public PointF calculatePoint(PointF startPoint, float length, float angle) {
        // x坐标
        float deltaX = (float) (Math.cos(Math.toRadians(angle)) * length);
        // y坐标
        float deltaY = (float) (Math.cos(Math.toRadians(angle - 180)) * length);
        // 旋转角度的坐标
        return new PointF(deltaX + startPoint.x, deltaY + startPoint.y);
    }
}
