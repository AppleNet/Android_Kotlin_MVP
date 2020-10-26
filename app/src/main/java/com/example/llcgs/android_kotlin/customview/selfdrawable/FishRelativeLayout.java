package com.example.llcgs.android_kotlin.customview.selfdrawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * com.example.llcgs.android_kotlin.customview.selfdrawable.FishRelativeLayout
 *
 * @author liulongchao
 * @since 2020/10/26
 */
public class FishRelativeLayout extends RelativeLayout {

    private Paint mPaint;
    private ImageView ivFish;
    private FishDrawable fishDrawable;
    private float touchX, touchY;
    private float ripple;
    private float alpha;

    public float getRipple() {
        return ripple;
    }

    public void setRipple(float ripple) {
        // 透明度的变化
        alpha = 100 * (1 - ripple);
        this.ripple = ripple;
    }

    public FishRelativeLayout(Context context) {
        this(context, null);
    }

    public FishRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FishRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context context
     */
    private void init(Context context) {
        // ViewGroup 默认不执行onDraw方法
        setWillNotDraw(false);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);

        ivFish = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivFish.setLayoutParams(layoutParams);

        fishDrawable = new FishDrawable();
        ivFish.setImageDrawable(fishDrawable);

        addView(ivFish);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "ripple", 0, 1f).setDuration(1000);
        objectAnimator.start();

        makeTrial();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制水波纹(由小变大，由不透明变透明)
        mPaint.setAlpha((int) alpha);
        canvas.drawCircle(touchX, touchY, ripple * 150, mPaint);

        invalidate();
    }

    private void makeTrial() {
        // 鱼的重心，相对ImageView坐标
        PointF fishRelativeMiddle = fishDrawable.getMiddlePoint();
        // 鱼的中心，绝对坐标
        PointF fishMiddle = new PointF(ivFish.getX() + fishRelativeMiddle.x, ivFish.getY() + fishRelativeMiddle.y);
        // 鱼头圆心坐标 --- 控制点1
        PointF fishHead = new PointF(ivFish.getX() + fishDrawable.getHeadPoint().x, ivFish.getY() + fishDrawable.getHeadPoint().y);
        // 点击坐标 ---- 结束点坐标
        PointF touch = new PointF(touchX, touchY);
        float angle = includeAngle(fishMiddle, fishHead, touch) / 2;
        float delta = includeAngle(fishMiddle, new PointF(fishMiddle.x + 1, fishMiddle.y), fishHead);
        // 控制点2坐标
        PointF controlPoint = fishDrawable.calculatePoint(fishMiddle, fishDrawable.getHEAD_RADIUS()*1.6f, angle + delta);

        Path path = new Path();
        path.moveTo(fishMiddle.x - fishRelativeMiddle.x
                , fishMiddle.y - fishRelativeMiddle.y);
        path.cubicTo(fishHead.x - fishRelativeMiddle.x, fishHead.y - fishRelativeMiddle.y,
                controlPoint.x - fishRelativeMiddle.x, controlPoint.y - fishRelativeMiddle.y,
                touchX - fishRelativeMiddle.x, touchY - fishRelativeMiddle.y);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivFish, "x", "y", path);
        objectAnimator.setDuration(2000);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fishDrawable.setFrequence(1f);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                fishDrawable.setFrequence(2f);
            }
        });

        PathMeasure pathMeasure = new PathMeasure(path, false);
        float[] tan = new float[2];
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 执行了整个周期的百分之多少
                float animatedFraction = animation.getAnimatedFraction();
                pathMeasure.getPosTan(pathMeasure.getLength() * animatedFraction, null, tan);
                float angle = (float) Math.toDegrees(Math.atan2(-tan[1], tan[0]));
                fishDrawable.setFishMainAngle(angle);
            }
        });
        objectAnimator.start();
    }

    public float includeAngle(PointF O, PointF A, PointF B)  {
        // cosAOB
        // OA*OB = (Ax - Ox) * (Bx - Ox) + (Ay - Oy) * (By - Oy)
        float AOB = (A.x - O.x) * (B.x - O.x) + (A.y - O.y) * (B.y - O.y);
        // OA长度
        float OALength = (float) Math.sqrt((A.x - O.x)*(A.x - O.x) + (A.y - O.y)*(A.y - O.y));
        // OB长度
        float OBLength = (float) Math.sqrt((B.x - O.x)*(B.x - O.x) + (B.y - O.y)*(B.y - O.y));
        float cosAOB = AOB / (OALength * OBLength);
        // 反余弦
        float angleAOB = (float) Math.toDegrees(Math.acos(cosAOB));

        // AB连线与X的夹角tan值 - OB与X轴的夹角的tan值
        float direction = (A.y - B.y)/(A.x - B.x) - (O.y - B.y) / (O.x - B.x);

        if (direction == 0) {
            if (AOB >= 0) {
                return 0;
            } else {
                return 180;
            }
        } else {
            if (direction > 0) {
                return -angleAOB;
            } else {
                return angleAOB;
            }
        }

    }
}
