package com.example.llcgs.android_kotlin.customview.selfview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class SimpleColorChangeTextView extends AppCompatTextView {

    private static final String text = "Hello world";
    private float mPercent = 0.0f;
    /**
     *  创建画笔
     * */
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint.FontMetrics fontMetrics;

    public float getPercent() {
        return mPercent;
    }

    public void setPercent(float mPercent) {
        this.mPercent = mPercent;
        invalidate();
    }

    public SimpleColorChangeTextView(Context context) {
        super(context);
    }

    public SimpleColorChangeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleColorChangeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCenterBlackText(canvas);
        drawCenterRedText(canvas);
    }

    /**
     *  画中心文字
     * */
    private void drawCenterBlackText(Canvas canvas) {
        // 第一层
        canvas.save();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(80);
        paint.setTextAlign(Paint.Align.LEFT);
        fontMetrics = paint.getFontMetrics();
        // 获取文字的宽度
        float width = paint.measureText(text);
        float baseLineX = (float) (getWidth() / 2  - width / 2);
        float baseLineX_X = baseLineX + width * mPercent;
        float baseLineY = (float) (getHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent);
        Rect rect = new Rect((int) baseLineX_X, 0, getWidth(), getHeight());
        canvas.clipRect(rect);
        canvas.drawText(text, baseLineX, baseLineY, paint);
        canvas.restore();
    }

    /**
     *  画中心文字
     * */
    private void drawCenterRedText(Canvas canvas) {
        canvas.save();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(80);
        paint.setTextAlign(Paint.Align.LEFT);
        fontMetrics = paint.getFontMetrics();
        // 获取文字的宽度
        float width = paint.measureText(text);
        float baseLineX = (float) (getWidth() / 2  - width / 2);
        float baseLineY = (float) (getHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent);
        float right = baseLineX + width * mPercent;
        // 裁剪
        Rect rect = new Rect((int) baseLineX, 0, (int) right, getHeight());
        canvas.clipRect(rect);
        canvas.drawText(text, baseLineX, baseLineY, paint);
        canvas.restore();
    }
}
