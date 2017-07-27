package com.example.llcgs.android_kotlin.classandobject.delegation

import android.os.Bundle
import android.util.Log
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.classandobject.delegation.presenter.impl.SixteenPresenter
import com.example.llcgs.android_kotlin.classandobject.delegation.view.SixteenView

/**
 * 委托
 *   类委托
 *
 * */

class SixteenActivity : BaseActivity<SixteenView, SixteenPresenter>() {

    override fun createPresenter()= SixteenPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sixteen)

    // 委托模式已经证明是实现继承的⼀个很好的替代⽅式， ⽽Kotlin可以零样板代码地原⽣⽀持它。 类Derived可以继承⼀个接口Base ，并将其所有共有的方法委托给⼀个指定的对象
        val b = BaseImpl(100)
        Derived(b).print()

        // 伪多继承
        // 不需要重写Runnable中的run方法 但是我依然可以调用run方法。
        // 将Runnable的run方法的实现 委托给了参数runnable的具体实现
        // 所以这叫类委托
        // 类似java的代理模式

        // 其实就是自己的类不想实现这个接口下的方法 通过一个已经实现的类 来代理这个方法的实现

    }
}

interface Base{
    fun print()

    fun print1()
}

class BaseImpl(val x:Int) : Base{
    override fun print() {
        Log.d("MainActivity", "x: ${x}")
    }

    override fun print1() {
        Log.d("MainActivity", "x: ${x}")
    }
}

class Derived(b: Base) : Base by b




class MyRunnable: Runnable{
    override fun run() {
        Log.d("MainActivity", "实现runnable接口")
    }
}

class HandleRunnable(runnable: MyRunnable) : Runnable by runnable{

}