package com.example.llcgs.android_kotlin.customview.selfview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.llcgs.android_kotlin.R;


public class ShaderView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Shader shader;
    private Bitmap bitmap;

    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        // 线性渐变
//        shader = new LinearGradient(0, 0, 540, 972, Color.parseColor("#E91E63"),
//                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
//
//        // 辐射渐变
//        shader = new RadialGradient(300, 300, 200, Color.parseColor("#E91E63"),
//                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);

        // BitmapShader
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.zeus);
        // shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // paint.setShader(shader);
        paint.setColorFilter(new LightingColorFilter(0x00ffff, 0x000000));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 圆心坐标
//        canvas.drawCircle(300, 300, 200, paint);
//        canvas.drawRect(0 + Utils.dp2px(10),
//                0 + Utils.dp2px(10),
//                getWidth() - Utils.dp2px(10),
//                getHeight() - Utils.dp2px(10),
//                paint);
        canvas.drawBitmap(bitmap, 200, 300, paint);
    }
}
