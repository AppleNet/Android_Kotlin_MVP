// 文件名为EightActivity.kt
package com.example.llcgs.android_kotlin.kotlin.classandobject.modifier

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.kotlin.classandobject.extensions.NineActivity
import com.example.llcgs.android_kotlin.kotlin.classandobject.modifier.presenter.impl.EightPresenter
import com.example.llcgs.android_kotlin.kotlin.classandobject.modifier.view.EightView
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_eight.*

/**
 * 可见性修饰符
 *  1.包名
 *  2.类和接口
 *     构造函数
 *     局部声明
 *  3.模块
 * */

class EightActivity : BaseActivity<EightView, EightPresenter>(), EightView {

    override fun createPresenter()= EightPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eight)

        RxView.clicks(button6).subscribe {
            startActivity(Intent(this, NineActivity::class.java))
        }

        // 包名
        // 函数、属性和类、对象和接⼝可以在顶层声明，即直接在包内
        /**
         *  如果你不指定任何可⻅性修饰符，默认为 public，这意味着你的声明 将随处可⻅；
            如果你声明为 private ，它只会在声明它的⽂件内可⻅；
            如果你声明为 internal ，它会在相同模块内随处可⻅；
            protected 不适⽤于顶层声明。
         * */

        // 类和接口
        /**
         *  对于类内部声明的成员：
            private 意味着只在这个类内部（包含其所有成员）可⻅；
            protected ⸺ 和 private ⼀样 + 在⼦类中可⻅。
            internal ⸺ 能⻅到类声明的  本模块内 的任何客⼾端都可⻅其 internal 成员；
            public ⸺ 能⻅到类声明的任何客⼾端都可⻅其 public 成员。
            注意 对于Java⽤⼾：Kotlin 中外部类不能访问内部类的 private 成员。
            如果你覆盖⼀个 protected 成员并且没有显式指定其可⻅性，该成员还会是 protected 可⻅性
         * */

        // 构造函数

        // 局部声明

        //模块
        // 可⻅性修饰符 internal 意味着该成员只在相同模块内可⻅。更具体地说， ⼀个模块是编译在⼀起的⼀套 Kotlin ⽂件
        // ⼀个 IntelliJ IDEA 模块
        // ⼀个 Maven 或者 Gradle 项⽬
        // ⼀次 ＜kotlinc＞ Ant 任务执⾏所编译的⼀套⽂件
    }

    private fun foo() {} // 在 example.kt 内可⻅

    public var bar: Int = 5 // 该属性随处可⻅
        private set // setter 只在 example.kt 内可⻅

    internal val baz = 6 // 相同模块内可⻅
}


// 类和接口
open class Outer {
    private val a = 1
    protected open val b = 2
    internal val c = 3
    val d = 4 // 默认 public
    protected class Nested {
        public val e: Int = 5
    }
}
class Subclass : Outer() {
    // a 不可⻅
    // b、c、d 可⻅
    // Nested 和 e 可⻅
    override val b = 5 // “b”为 protected
}
class Unrelated(o: Outer) {
    // o.a、o.b 不可⻅
    // o.c 和 o.d 可⻅（相同模块）
    // Outer.Nested 不可⻅，Nested::e 也不可⻅
}
