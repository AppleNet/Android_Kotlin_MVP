package com.example.llcgs.android_kotlin.customview.selfdrawable;

import android.animation.ValueAnimator;
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
    /**
     * 路径
     */
    private Path mPath;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 鱼的重心
     */
    private PointF middlePoint;
    /**
     * 鱼的主要朝向角度，鱼头与X轴的夹角
     */
    private float fishMainAngle = 0.0f;
    /**
     * 鱼的长度值
     */
    // 绘制鱼头的半径
    private final int HEAD_RADIUS = 100;
    // 鱼身的长度
    private float BODY_LENGTH = HEAD_RADIUS * 3.2f;
    // 鱼鳍的长度
    private float FINS_LENGTH = 1.3f * HEAD_RADIUS;
    // 寻找鱼鳍起始点坐标点线长
    private float FIND_FINS_LENGTH = 0.9f * HEAD_RADIUS;
    // 大圆的半径
    private float BIG_CIRCLE_RADIUS = 0.7f * HEAD_RADIUS;
    // 中圆的半径
    private float MIDDLE_CIRCLE_RADIUS = 0.6f * BIG_CIRCLE_RADIUS;
    // 小圆的半径
    private float SMALL_CIRCLE_RADIUS = 0.4f * MIDDLE_CIRCLE_RADIUS;
    // 寻找中圆圆心的线长
    private final float FIND_MIDDLE_CIRCLE_LENGTH = BIG_CIRCLE_RADIUS * (0.6f + 1);
    // 寻找小圆圆心的线长
    private final float FIND_SMALL_CIRCLE_LENGTH = MIDDLE_CIRCLE_RADIUS * (0.4f + 2.7f);
    // 寻找大三角形底边中心点的线长
    private final float FIND_TRIANGLE_LENGTH = FIND_SMALL_CIRCLE_LENGTH * 2.7f;
    // 透明度设置
    private final int OTHER_ALPHA = 110;
    // 鱼身透明度
    private final int BODY_ALPHA = 150;

    public FishDrawable() {
        init();
    }

    private void init() {
        mPath = new Path();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setARGB(OTHER_ALPHA, 244, 92, 71);

        middlePoint = new PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
    }

    /**
     * 画鱼
     *
     * @param canvas canvas
     */
    @Override
    public void draw(@NonNull Canvas canvas) {
        // 绘制小鱼
        float fishAngle = fishMainAngle;
        // 鱼头圆心坐标
        PointF headPoint = calculatePoint(middlePoint, BODY_LENGTH / 2, fishAngle);
        // 绘制鱼头
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint);

        // 右鱼鳍 (二阶贝塞尔曲线)
        PointF rightFinsPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle - 110);
        makeFins(canvas, rightFinsPoint, fishAngle, true);

        // 左鱼鳍 (二阶贝塞尔曲线)
        PointF leftFinsPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle + 110);
        makeFins(canvas, leftFinsPoint, fishAngle, false);

        PointF bodyBottomCenterPoint = calculatePoint(headPoint, BODY_LENGTH, fishAngle - 180);
        // 画节肢1
        PointF middleCenterPoint = makeSegment(canvas, bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, MIDDLE_CIRCLE_RADIUS, FIND_MIDDLE_CIRCLE_LENGTH, fishAngle, true);

        // 画节肢2
        makeSegment(canvas, middleCenterPoint, MIDDLE_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS, FIND_SMALL_CIRCLE_LENGTH, fishAngle, false);

        // 画尾巴
        makeTriangle(canvas, middleCenterPoint, fishAngle, FIND_TRIANGLE_LENGTH, BIG_CIRCLE_RADIUS);
        makeTriangle(canvas, middleCenterPoint, fishAngle, FIND_TRIANGLE_LENGTH - 10, BIG_CIRCLE_RADIUS - 20);

        // 画身体
        makeBody(canvas, headPoint, bodyBottomCenterPoint, fishAngle);
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
     * 画身体
     *
     * @param canvas                canvas
     * @param headPoint             headPoint
     * @param bodyBottomCenterPoint bodyBottomCenterPoint
     * @param fishAngle             fishAngle
     */
    private void makeBody(Canvas canvas, PointF headPoint, PointF bodyBottomCenterPoint, float fishAngle) {
        // 身体的四个点
        PointF topLeftPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle + 90);
        PointF topRightPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle - 90);
        PointF bottomLeftPoint = calculatePoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, fishAngle + 90);
        PointF bottomRightPoint = calculatePoint(headPoint, BIG_CIRCLE_RADIUS, fishAngle - 90);

        // 二阶贝塞尔曲线的控制点 决定鱼的胖瘦
        PointF controlLeft = calculatePoint(headPoint, BODY_LENGTH * 0.56f, fishAngle + 130);
        PointF controlRight = calculatePoint(headPoint, BODY_LENGTH * 0.56f, fishAngle - 130);

        // 绘制
        mPath.reset();
        mPath.moveTo(topLeftPoint.x, topLeftPoint.y);
        mPath.quadTo(controlLeft.x, controlLeft.y, bottomLeftPoint.x, bottomLeftPoint.y);
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        mPath.quadTo(controlRight.x, controlRight.y, topRightPoint.x, topRightPoint.y);
        mPaint.setAlpha(BODY_ALPHA);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 画鱼尾
     *
     * @param canvas     canvas
     * @param startPoint canvas
     * @param fishAngle  fishAngle
     */
    private void makeTriangle(Canvas canvas, PointF startPoint, float fishAngle, float findTriangleLength, float circleRadius) {
        // 三角形底边的中心坐标
        PointF centerPoint = calculatePoint(startPoint, findTriangleLength, fishAngle - 180);
        // 三角形底边
        PointF leftPoint = calculatePoint(centerPoint, circleRadius, fishAngle + 90);
        PointF rightPoint = calculatePoint(centerPoint, circleRadius, fishAngle - 90);
        mPath.reset();
        mPath.moveTo(startPoint.x, startPoint.y);
        mPath.lineTo(leftPoint.x, leftPoint.y);
        mPath.lineTo(rightPoint.x, rightPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 画节肢
     *
     * @param canvas                canvas
     * @param bottomCenterPoint     pointF
     * @param bigCircleRadius       bigCircleRadius
     * @param middleCircleRadius    middleCircleRadius
     * @param findSmallCircleLength findSmallCircleLength
     * @param fishAngle             fishAngle
     */
    private PointF makeSegment(Canvas canvas, PointF bottomCenterPoint,
                               float bigCircleRadius, float middleCircleRadius, float findSmallCircleLength, float fishAngle,
                               boolean hasBigCircle) {
        // 梯形上底圆的圆心
        PointF upperCenterPoint = calculatePoint(bottomCenterPoint, findSmallCircleLength, fishAngle - 180);
        // 梯形的四个点
        PointF bottomLeftPoint = calculatePoint(bottomCenterPoint, bigCircleRadius, fishAngle + 90);
        PointF bottomRightPoint = calculatePoint(bottomCenterPoint, bigCircleRadius, fishAngle - 90);
        PointF upperLeftPoint = calculatePoint(upperCenterPoint, middleCircleRadius, fishAngle + 90);
        PointF upperRightPoint = calculatePoint(upperCenterPoint, middleCircleRadius, fishAngle - 90);
        if (hasBigCircle) {
            // 画大圆
            canvas.drawCircle(bottomCenterPoint.x, bottomCenterPoint.y, bigCircleRadius, mPaint);
        }
        // 画小圆
        canvas.drawCircle(upperCenterPoint.x, upperCenterPoint.y, middleCircleRadius, mPaint);
        // 画梯形
        mPath.reset();
        mPath.moveTo(upperLeftPoint.x, upperLeftPoint.y);
        mPath.lineTo(upperRightPoint.x, upperRightPoint.y);
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        mPath.lineTo(bottomLeftPoint.x, bottomLeftPoint.y);
        canvas.drawPath(mPath, mPaint);
        return upperCenterPoint;
    }

    /**
     * 画鱼鳍
     *
     * @param canvas     canvas
     * @param startPoint startPoint
     * @param fishAngle  fishAngle
     * @param isRight    isRight
     */
    private void makeFins(Canvas canvas, PointF startPoint, float fishAngle, boolean isRight) {
        float controlAngle = 115;
        // 鱼鳍的终点 (二阶贝塞尔曲线的终点)
        PointF endPoint = calculatePoint(startPoint, FINS_LENGTH, fishAngle - 180);
        // 控制点
        PointF controlPoint = calculatePoint(startPoint, FINS_LENGTH * 1.8f,
                isRight ? fishAngle - controlAngle : fishAngle + controlAngle);
        // 绘制
        mPath.reset();
        // 将画笔移动到起始点
        mPath.moveTo(startPoint.x, startPoint.y);
        // 二阶贝塞尔曲线
        mPath.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y);
        canvas.drawPath(mPath, mPaint);
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
