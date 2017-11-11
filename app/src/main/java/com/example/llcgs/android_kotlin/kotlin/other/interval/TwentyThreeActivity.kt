package com.example.llcgs.android_kotlin.kotlin.other.interval

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.kotlin.other.interval.presenter.impl.TwentyThreePresenter
import com.example.llcgs.android_kotlin.kotlin.other.interval.view.TwentyThreeView
import com.example.llcgs.android_kotlin.kotlin.other.typecheckchange.TwentyFourActivity
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_twenty_three.*

/**
 *  区间
 *   1.它是如何工作的
 *   2.一些使用函数
 *     1.rangeTo
 *     2.downTo
 *     3.reversed
 *     4.step
 * */

class TwentyThreeActivity : BaseActivity<TwentyThreeView, TwentyThreePresenter>(), TwentyThreeView {


    override fun createPresenter()= TwentyThreePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twenty_three)

        RxView.clicks(button21).subscribe {
            startActivity(Intent(this@TwentyThreeActivity, TwentyFourActivity::class.java))
        }

    // 区间
        // 区间表达式由具有操作符形式 .. 的 rangeTo 函数辅以 in 和 !in 形成。 区间是为任何可比较类型定义的，但对于整型原生类型，它有⼀个优化的实现
        val i = 0
        if (i in 1..10){ // 等同于 1 <= i && i <= 10
            i.logD()
        }

        // 整型区间（IntRange 、LongRange、CharRange）有⼀个额外的特性：它们可以迭代。编译器负责将其转换为类似Java的基于索引的 for-循环而无额外开销
        for (j in 1..10){ // 输出1-10
            j.logD()
        }

        for (j in 10..1){ // 什么都不输出
            j.logD()
        }

        // 如果想让10-1输出，应该使用dowmTo 倒序输出
        for (j in 10 downTo 1){
            j.logD()
        }

        // 以不等于 1 的任意步长迭代数字？ 当然没问题， step() 函数有助于此
        for (m in 1..10 step 2){ // 输出1，3，5，7，9 从1开始每次加2
            m.logD()
        }

        // 创建⼀个不包括其结束元素的区间，可以使⽤ until 函数
        for (n in 1 until 10){ // 输出1-9 不包含10
            n.logD()
        }

        // 如何工作
        // 区间实现了该库中的⼀个公共接⼝：ClosedRange<T>

        // ClosedRange<T> 在数学意义上表示⼀个闭区间，它是为可比较类型定义的。 它有两个端点：start 和 endInclusive 他们都包含在区间内。 其主
        // 要操作是 contains ，通常以 in/!in 操作符形式使用

        // 整型数列（IntProgression、LongProgression、CharProgression ）表示等差数列。 数列由 first 元素、last元素和非零的step定义。
        // 第⼀个元素是 first ，后续元素是前⼀个元素加上 step 。 last元素总会被迭代命中，除非该数列是空的

        // 数列是 Iterable<N> 的子类型，其中N分别为 Int 、 Long 或者 Char ，所以它可用于 for-循环以及像 map 、filter 等函数中

        // 一些使用函数
        // rangTo
        // 排序
        val rangeTo = 1.rangeTo(2)
        rangeTo.first.logD()
        rangeTo.forEach {
            it.logD()
        }

        // reversed() 反转 IntProgression的拓展函数
        for (i in (1..10).reversed()){
            i.logD()
        }

    }
}
