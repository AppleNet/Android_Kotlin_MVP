package com.example.llcgs.android_kotlin.animator.selfview

import android.animation.TypeEvaluator
import java.util.concurrent.Executors

/**
 * com.example.llcgs.android_kotlin.animator.selfview.PointEvaluator
 * @author liulongchao
 * @since 2018/9/29
 */
class PointEvaluator: TypeEvaluator<Any> {

    override fun evaluate(fraction: Float, startValue: Any?, endValue: Any?): Any {
        val startPoint: Point = startValue as Point
        val endPoint: Point = endValue as Point
        val x = startPoint.x + fraction*(endPoint.x - startPoint.x)
        val y = startPoint.y + fraction*(endPoint.y - startPoint.y)

        Executors.newFixedThreadPool(2)

        return Point(x, y)
    }
}