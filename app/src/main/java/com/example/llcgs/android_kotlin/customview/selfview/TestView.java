package com.example.llcgs.android_kotlin.customview.selfview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class TestView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();



    public TestView(Context context) {
        super(context);
    }

    /**
     *  给系统自动加载的时候 使用的
     *
     * */
    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        path.reset();
        // 画一个方形
        path.addRect(getWidth() / 2 - 150, getHeight() / 2 - 300, getWidth() / 2 + 150, getHeight() / 2, Path.Direction.CCW);
        // 画一个圆形
        path.addCircle(getWidth() / 2, getHeight() / 2, 150, Path.Direction.CW);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawCircle(getWidth() / 2, getHeight() / 2,
////                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, Resources.getSystem().getDisplayMetrics()), paint);
        path.setFillType(Path.FillType.WINDING);
        canvas.drawPath(path, paint);
    }
}
