package com.example.llcgs.android_kotlin.animator.selfview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.animation.ValueAnimator
import android.view.animation.BounceInterpolator


/**
 * com.example.llcgs.android_kotlin.animator.selfview.MyAnimView
 * @author liulongchao
 * @since 2018/9/29
 */
class MyAnimView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val RADIUS = 50f
    var currentPoint: Point? = null
    var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (currentPoint == null){
            currentPoint = Point(RADIUS, RADIUS)
            drawCircle(canvas)
            startAnimation()
        }else{
            drawCircle(canvas)
        }
    }

    private fun drawCircle(canvas: Canvas?){
        val x = currentPoint?.x
        val y = currentPoint?.y
        canvas?.drawCircle(x?:0f, y?:0f, RADIUS, paint)
    }

    private fun startAnimation(){
        val startPoint = Point(RADIUS, RADIUS)
        val endPoint = Point(width - RADIUS, height - RADIUS)
        val anim = ValueAnimator.ofObject(PointEvaluator(), startPoint, endPoint)
        anim.addUpdateListener { animation ->
            currentPoint = animation.animatedValue as Point
            invalidate()
        }
        anim.interpolator = BounceInterpolator()
        anim.duration = 5000
        anim.start()
    }
}