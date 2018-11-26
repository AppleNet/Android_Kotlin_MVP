package com.example.llcgs.android_kotlin.customview.selfview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.llcgs.android_kotlin.R;

/**
 * com.example.llcgs.android_kotlin.customview.selfview.MyCustomView
 *
 * @author liulongchao
 * @since 2018/11/12
 */
public class MyCustomView extends View {

    /**
     *  简易版自定义View
     *      继承View，重写onDraw方法，用canvas画自定义控件
     * */

    private Paint paint;
    private Context context;
    private Rect rect;
    private AttributeSet attrs;

    public MyCustomView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        initView();
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        initView();
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        this.attrs = attrs;
        initView();
    }

    private void initView(){
        paint = new Paint();
        rect = new Rect(10, 10, 300, 300);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyCustomView);
        float textSize = typedArray.getDimensionPixelSize(R.styleable.MyCustomView_textSize, 30);
        int textColor = typedArray.getColor(R.styleable.MyCustomView_textColor, 0x990000FF);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(rect, paint);
        paint.setColor(Color.parseColor("#FFFFFF"));
        canvas.drawText("MyCustomView", 15, 15, paint);
    }
}
