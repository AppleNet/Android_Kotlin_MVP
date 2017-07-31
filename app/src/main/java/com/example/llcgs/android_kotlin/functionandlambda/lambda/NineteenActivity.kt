package com.example.llcgs.android_kotlin.functionandlambda.lambda

import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.functionandlambda.lambda.presenter.impl.NineteenPresenter
import com.example.llcgs.android_kotlin.functionandlambda.lambda.view.NineteenView
import com.gomejr.myf.core.kotlin.logD
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock

/**
 *  高阶函数和lambda表达式
 *    1.高阶函数
 *      it，单个函数的隐式名称
 *      下划线用于未使用的变量
 *      在lambda表达式中结构
 *    2.内联函数
 *    3.lambda表达式和匿名函数
 *      函数类型
 *      lambda表达式语法
 *      匿名函数
 *      闭包
 *      带接收者的函数字面值
 *
 * **/

class NineteenActivity : BaseActivity<NineteenView, NineteenPresenter>(), NineteenView {

    override fun createPresenter()= NineteenPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nineteen)

    // 高阶函数
        // 高阶函数是将函数用作参数或者返回值，这种函数的一个很好例子是lock(),它接受一个对象和一个函数，获取锁，运行函数并释放锁
        val lock: Lock = object : Lock {
            override fun tryLock(): Boolean {
                return false
            }

            override fun tryLock(time: Long, unit: TimeUnit?): Boolean {
                return false
            }

            override fun lockInterruptibly() {
            }

            override fun newCondition(): Condition? {
                return null
            }

            override fun lock() {
                "start lock".logD()
            }

            override fun unlock() {
                "start unlock".logD()
            }
        }
        fun getName() = "Kobe"
        // 调用高阶函数，当函数以参数的形式传递的时候，使用::函数名字 来调用
        // 用三种调用方式

        // 第一种形式的调用
        val lock1 = lock(lock, ::getName)
        lock1.forEach { it.logD() }

        // 第二种形式的调用
        // 使用lambda表达式调用
        // lambda 表达式总是被大括号括着，
        // 其参数（如果有的话）在 -> 之前声明（参数类型可以省略），
        // 函数体（如果存在的话）在 -> 后⾯
        val lock2 = lock(lock, { getName() })
        lock2.forEach { it.logD() }

        // 第三种形式的调用
        // 在 Kotlin 中有⼀个约定，如果函数的最后⼀个参数是⼀个函数，并且你传递⼀个 lambda 表达式作为相应的参数，你可以在圆括号之外指定它
        val lock3 = lock(lock) { getName() }
        lock3.forEach { it.logD() }

    }

    // 声明一个高阶函数
    fun <T> lock(lock: Lock, body:() -> T): List<T>{
        lock.lock()
        try {
            val list = ArrayList<T>()
            for (x in 1..10){
                list.add(body())
            }
            return list
        }finally {
            lock.unlock()
        }
    }
}
